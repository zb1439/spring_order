package com.zbfan.spring_order.service;

import com.zbfan.spring_order.enums.ResultEnum;
import com.zbfan.spring_order.exception.SellException;
import com.zbfan.spring_order.utils.KeyUtil;
import com.zbfan.spring_order.utils.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillService {

    private static final int TIMEOUT = 10 * 1000;

    @Autowired
    private RedisLock redisLock;

    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;

    static {
        /**
         * Mock Dataset
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 100000);
        stock.put("123456", 100000);
    }

    public String querySecKillProductInfo(String productId) {
        return "Limited Special Sale for "
                + products.get(productId)
                + " remains：" + stock.get(productId)
                + " ordered："
                + orders.size();
    }

    public void orderProductMockDiffUser(String productId) {
        // acquire lock
        String value = String.valueOf(System.currentTimeMillis() + TIMEOUT);
        if (!redisLock.lock(productId, value)) {
            throw new SellException(ResultEnum.SECKILL_FAILURE);
        }

        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException(100, "活动结束");
        } else {
            //2. Order with simulated different openIds
            orders.put(KeyUtil.genUniqueKey(), productId);
            //3. Decrease stock
            stockNum = stockNum - 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

        // release lock
        redisLock.unlock(productId, value);
    }
}
