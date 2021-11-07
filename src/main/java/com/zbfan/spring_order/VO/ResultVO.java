package com.zbfan.spring_order.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 8628328759056151540L;

    private Integer code;

    private String msg;

    private T data;
}
