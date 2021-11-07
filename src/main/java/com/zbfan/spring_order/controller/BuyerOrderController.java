package com.zbfan.spring_order.controller;

import com.zbfan.spring_order.VO.ResultVO;
import com.zbfan.spring_order.converter.OrderForm2OrderDTOConverter;
import com.zbfan.spring_order.dto.OrderDTO;
import com.zbfan.spring_order.enums.ResultEnum;
import com.zbfan.spring_order.exception.SellException;
import com.zbfan.spring_order.form.OrderForm;
import com.zbfan.spring_order.service.BuyerService;
import com.zbfan.spring_order.service.OrderService;
import com.zbfan.spring_order.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    // Create order
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[Order creation]: wrong parameters, {}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (orderDTO.getOrderDetailList().isEmpty()) {
            log.error("[Order creation]: no items in the order");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO res = orderService.createOrder(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", res.getOrderId());
        return ResultVOUtil.success(map);
    }

    // View orders as a page
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> showList(@RequestParam("openid") String openid,
                                             @RequestParam(value="page", defaultValue="0") Integer page,
                                             @RequestParam(value="size", defaultValue="10") Integer size) {
        if (openid == null || openid.length() == 0) {
            log.error("[Order listing]: openid is empty");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Page<OrderDTO> res = orderService.findList(openid, PageRequest.of(page, size));
        return ResultVOUtil.success(res.getContent());
    }

    // View order details
    @GetMapping("/detail")
    public ResultVO<OrderDTO> showDetail(@RequestParam("openid") String openid,
                                         @RequestParam("orderId") String orderId) {
        if (openid == null || openid.length() == 0) {
            log.error("[Order detail]: openid is empty");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if (orderId == null || orderId.length() == 0) {
            log.error("[Order detail]: orderId is empty");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        return ResultVOUtil.success(buyerService.findOneOrder(openid, orderId));
    }

    // Cancel order
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        if (openid == null || openid.length() == 0) {
            log.error("[Order cancellation]: openid is empty");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if (orderId == null || orderId.length() == 0) {
            log.error("[Order cancellation]: orderId is empty");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
