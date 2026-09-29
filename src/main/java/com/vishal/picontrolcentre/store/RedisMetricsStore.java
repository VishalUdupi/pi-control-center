package com.vishal.picontrolcentre.store;

import com.vishal.picontrolcentre.dto.CpuStatsResponse;
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

    public void saveCpuStats(CpuStatsResponse response){
        System.out.println("Store running");
//        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
//        hashOperations.put("system:cpu", "core", "4");
        HashOperations<String, String, String> cpuHOps = redisTemplate.opsForHash();
        Integer cores = response.cores();
        Double iowait = response.iowait();
        Double total = response.total();
        Double user = response.user();

        cpuHOps.put("system:cpu", "cores", cores.toString());
        cpuHOps.put("system:cpu", "iowait", iowait.toString());
        cpuHOps.put("system:cpu", "total", total.toString());
        cpuHOps.put("system:cpu", "user", user.toString());

    }


}
