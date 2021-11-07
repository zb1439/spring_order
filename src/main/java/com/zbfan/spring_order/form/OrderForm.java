package com.zbfan.spring_order.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OrderForm {

    @NotBlank(message = "name must not be empty")
    private String name;

    @NotBlank(message = "phone number must not be empty")
    private String phone;

    @NotBlank(message = "address must not be empty")
    private String address;

    @NotBlank(message = "address must not be empty")
    private String openid;

    @NotBlank(message = "shopping cart must not be empty")
    private String items;
}
