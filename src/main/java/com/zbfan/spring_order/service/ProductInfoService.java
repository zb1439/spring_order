package com.zbfan.spring_order.service;


import com.zbfan.spring_order.dataobject.ProductInfo;
import com.zbfan.spring_order.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findOnlineProducts();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo info);

    void increaseStock(List<CartDTO> cartDTOList);

    void decreaseStock(List<CartDTO> cartDTOList);

    ProductInfo onSale(String productId);

    ProductInfo offSale(String productId);
}
