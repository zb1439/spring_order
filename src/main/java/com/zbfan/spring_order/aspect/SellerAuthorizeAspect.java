package com.zbfan.spring_order.aspect;

import com.zbfan.spring_order.constant.CookieConstant;
import com.zbfan.spring_order.constant.RedisConstant;
import com.zbfan.spring_order.exception.SellException;
import com.zbfan.spring_order.exception.SellerAuthorizeException;
import com.zbfan.spring_order.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(public * com.zbfan.spring_order.controller.Seller*.*(..))" +
            "&& !execution(public * com.zbfan.spring_order.controller.SellerUserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("[Login Verification]: No token found in cookie");
            throw new SellerAuthorizeException();
        }

        String tokenValue = stringRedisTemplate.opsForValue()
                .get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("[Login Verification]: No token found in redis");
            throw new SellerAuthorizeException();
        }
    }
}
