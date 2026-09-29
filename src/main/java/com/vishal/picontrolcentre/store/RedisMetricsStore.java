package com.vishal.picontrolcentre.store;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMetricsStore {

    private final StringRedisTemplate redisTemplate;

    public RedisMetricsStore(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void saveCpuStats(){
        System.out.println("Store running");
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("system:cpu", "core", 4);


    }


}
