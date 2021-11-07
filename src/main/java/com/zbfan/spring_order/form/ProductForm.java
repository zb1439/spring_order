package com.zbfan.spring_order.form;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@Data
public class ProductForm {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    @Value("")
    private String productIcon;

    private Integer categoryType;
}
