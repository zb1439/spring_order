package com.zbfan.spring_order.service.impl;

import com.zbfan.spring_order.dataobject.SellerInfo;
import com.zbfan.spring_order.dataobject.repository.SellerInfoRepository;
import com.zbfan.spring_order.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}
