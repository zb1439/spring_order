package com.zbfan.spring_order.service;

import com.zbfan.spring_order.dataobject.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
