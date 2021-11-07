package com.zbfan.spring_order.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *
     * @param key openid
     * @param value currentTime + timeout (in timestamp format)
     * @return if a lock is acquired
     */
    public boolean lock(String key, String value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }

        String currentValue = redisTemplate.opsForValue().get(key);
        // if the lock has timed out
        if (!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            // If the following is executed simultaneously by different machines,
            // the thread acquiring lock cannot successfully unlock it
            // and will need another timeout processing next time.
            // The possibility is little, so we adopt it.
            String oldval = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldval) && oldval.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    public void unlock(String key, String value) {
        try {
            String curval = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(curval) && curval.equals(value)) {
                redisTemplate.delete(key);
            }
        } catch (Exception e) {
            log.error("[Redis Distributed Lock]: exception when unlocking, {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
