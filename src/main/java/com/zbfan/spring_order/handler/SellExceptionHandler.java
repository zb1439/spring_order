package com.zbfan.spring_order.handler;

import com.zbfan.spring_order.VO.ResultVO;
import com.zbfan.spring_order.config.ProjectUrl;
import com.zbfan.spring_order.exception.SellException;
import com.zbfan.spring_order.exception.SellerAuthorizeException;
import com.zbfan.spring_order.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrl projectUrl;

    @ExceptionHandler(value= SellerAuthorizeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAuthorizeException() {
        return new ModelAndView("redirect:"
                .concat(projectUrl.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat("/sell/seller/login"));
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handleSellException(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
