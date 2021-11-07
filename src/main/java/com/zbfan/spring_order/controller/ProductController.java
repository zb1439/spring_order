package com.zbfan.spring_order.controller;


import com.zbfan.spring_order.VO.ProductInfoVO;
import com.zbfan.spring_order.VO.ProductVO;
import com.zbfan.spring_order.VO.ResultVO;
import com.zbfan.spring_order.dataobject.ProductCategory;
import com.zbfan.spring_order.dataobject.ProductInfo;
import com.zbfan.spring_order.service.ProductCategoryService;
import com.zbfan.spring_order.service.ProductInfoService;
import com.zbfan.spring_order.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class ProductController {

    @Autowired
    private ProductInfoService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames="productList", key="#sellerId",
            condition = "#(sellerId != null) && (sellerId.length() >= 3)", unless = "#result.getCode() != 0")
    public ResultVO list(@RequestParam(name = "sellerId", required = false) String sellerId) {
        List<ProductInfo> onlineProductList = productService.findOnlineProducts();
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(
                onlineProductList.stream()
                        .map(ProductInfo::getCategoryType)
                        .collect(Collectors.toList())
        );
        List<ProductVO> dataList = new ArrayList<>();

        for (ProductCategory category : categoryList) {
            List<ProductInfoVO> foodsList = new ArrayList<>();
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(category.getCategoryType());
            productVO.setCategoryName(category.getCategoryName());
            for (ProductInfo product : onlineProductList) {
                if (product.getCategoryType().equals(category.getCategoryType())) {
                    Integer productCategory = product.getCategoryType();
                    ProductInfoVO infoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(product, infoVO);
                    foodsList.add(infoVO);
                }
            }
            productVO.setFoods(foodsList);
            dataList.add(productVO);
        }

        return ResultVOUtil.success(dataList);
    }
}
