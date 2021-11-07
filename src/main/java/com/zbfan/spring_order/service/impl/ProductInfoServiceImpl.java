package com.zbfan.spring_order.service.impl;

import com.zbfan.spring_order.dataobject.ProductInfo;
import com.zbfan.spring_order.dataobject.repository.ProductInfoRepository;
import com.zbfan.spring_order.dto.CartDTO;
import com.zbfan.spring_order.enums.ProductStatusEnum;
import com.zbfan.spring_order.enums.ResultEnum;
import com.zbfan.spring_order.exception.SellException;
import com.zbfan.spring_order.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    @Cacheable(cacheNames="productInfo", key="{#productId}")
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> productInfoOptional = repository.findById(productId);
        if (!productInfoOptional.isPresent()) {
            log.error("[Product Service]: could not find product id={}", productId);
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        return productInfoOptional.get();
    }

    @Override
    public List<ProductInfo> findOnlineProducts() {
        return repository.findByProductStatus(ProductStatusEnum.ONLINE.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @CachePut(cacheNames="productInfo", key="{#info.getProductId()}")
    public ProductInfo save(ProductInfo info) {
        return repository.save(info);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo info = findOne(cartDTO.getProductId());
            info.setProductStock(info.getProductStock() + cartDTO.getProductQuantity());
            repository.save(info);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo info = findOne(cartDTO.getProductId());
            info.setProductStock(info.getProductStock() - cartDTO.getProductQuantity());
            if (info.getProductStock() < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            repository.save(info);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = findOne(productId);
        if (!productInfo.getProductStatus().equals(ProductStatusEnum.OFFLINE.getCode())) {
            log.error("[Product Service]: expecting online product, but is offline, id={}", productId);
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.ONLINE.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = findOne(productId);
        if (!productInfo.getProductStatus().equals(ProductStatusEnum.ONLINE.getCode())) {
            log.error("[Product Service]: expecting offline product, but is online, id={}", productId);
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.OFFLINE.getCode());
        return repository.save(productInfo);
    }
}
