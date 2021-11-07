package com.zbfan.spring_order.service;

import com.zbfan.spring_order.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory findOne(Integer id);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> catList);

    ProductCategory save(ProductCategory category);
}
