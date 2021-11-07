package com.zbfan.spring_order.service.impl;

import com.zbfan.spring_order.config.WechatAccountConfig;
import com.zbfan.spring_order.dto.OrderDTO;
import com.zbfan.spring_order.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {
    @Autowired
    private WxMpService mpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStat"));
        templateMessage.setToUser("oz-aq6bHA8gYEytAO4y0EcnORFHg");
        List<WxMpTemplateData> templateData = Arrays.asList(
                new WxMpTemplateData("first", "test template message"),
                new WxMpTemplateData("keyword1", "spring_order"),
                new WxMpTemplateData("keyword2", "5102804419"),
                new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4", "$" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark", "thanks for your testing")
        );
        templateMessage.setData(templateData);
        try {
            mpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("[Order Status]: error sending template msg, {}", e.getMessage());
        }
    }
}
