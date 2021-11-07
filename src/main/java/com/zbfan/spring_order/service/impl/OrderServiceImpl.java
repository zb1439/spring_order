package com.zbfan.spring_order.service.impl;

import com.zbfan.spring_order.converter.OrderMaster2OrderDTOConverter;
import com.zbfan.spring_order.dataobject.OrderDetail;
import com.zbfan.spring_order.dataobject.OrderMaster;
import com.zbfan.spring_order.dataobject.ProductInfo;
import com.zbfan.spring_order.dataobject.repository.OrderDetailRepository;
import com.zbfan.spring_order.dataobject.repository.OrderMasterRepository;
import com.zbfan.spring_order.dto.CartDTO;
import com.zbfan.spring_order.dto.OrderDTO;
import com.zbfan.spring_order.enums.OrderStatusEnum;
import com.zbfan.spring_order.enums.PayStatusEnum;
import com.zbfan.spring_order.enums.ResultEnum;
import com.zbfan.spring_order.exception.SellException;
import com.zbfan.spring_order.service.OrderService;
import com.zbfan.spring_order.service.ProductInfoService;
import com.zbfan.spring_order.service.WebSocket;
import com.zbfan.spring_order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Id;
import javax.transaction.Transactional;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        BigDecimal totalPrice = new BigDecimal(0);
        String orderId = KeyUtil.genUniqueKey();

        for (OrderDetail detail : orderDTO.getOrderDetailList()) {
            String productId = detail.getProductId();
            ProductInfo info = productInfoService.findOne(productId);
            try {
                info.getProductPrice();
            } catch (EntityNotFoundException e) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            BigDecimal price = info.getProductPrice();
            totalPrice = price.multiply(new BigDecimal(detail.getProductQuantity())).add(totalPrice);

            BeanUtils.copyProperties(info, detail);
            detail.setDetailId(KeyUtil.genUniqueKey());
            detail.setOrderId(orderId);
            orderDetailRepository.save(detail);
        }

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(totalPrice);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterRepository.save(orderMaster);

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.getById(orderId);
        try {
            orderMaster.getOrderAmount();
        } catch (EntityNotFoundException e) {
            log.error("Order does not exist for order id {}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (orderDetails.size() == 0) {
            log.error("Order details for order id {} do not exist!", orderId);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        // No need to return details for pages
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[Order Cancellation]: Order status incorrect!");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster res = orderMasterRepository.save(orderMaster);
        if (res == null) {
            log.error("[Order Cancellation]: Update failed " + orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        if (orderDTO.getOrderDetailList().isEmpty()) {
            log.error("[Order Cancellation]: No order details in current order!" + orderMaster);
            throw new SellException(ResultEnum.ORDER_NO_DETAIL);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[Order Cancellation]: Order status incorrect!");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster res = orderMasterRepository.save(orderMaster);
        if (res == null) {
            log.error("[Order Cancellation]: Update failed " + orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        pushMessageService.orderStatus(orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[Order Cancellation]: Order status incorrect!");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster res = orderMasterRepository.save(orderMaster);
        if (res == null) {
            log.error("[Order Cancellation]: Update failed " + orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}
