package com.vishal.picontrolcentre.store;

import com.vishal.picontrolcentre.dto.*;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisMetricsStore {

    private final StringRedisTemplate redisTemplate;

    public RedisMetricsStore(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void saveCpuStats(CpuStatsResponse response){
        System.out.println("Store running");
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

    public void saveMemoryStats(MemoryStatsResponse response){
        HashOperations<String, String, String> memoryHOps = redisTemplate.opsForHash();
        Long active = response.active();
        Long free = response.free();
        Double percent = response.percent();
        Long total = response.total();
        Long used = response.used();

        memoryHOps.put("system:memory", "total", total.toString());
        memoryHOps.put("system:memory", "used", used.toString());
        memoryHOps.put("system:memory", "active", active.toString());
        memoryHOps.put("system:memory", "percent", percent.toString());
        memoryHOps.put("system:memory", "free", free.toString());


    }

    public void saveLoadStats(LoadStatsResponse response){
        HashOperations<String, String, String> loadHOps = redisTemplate.opsForHash();
        Double min1 = response.min1();
        Double min5 = response.min5();
        Double min15 = response.min15();

        loadHOps.put("system:load", "min1", min1.toString());
        loadHOps.put("system:load", "min5", min5.toString());
        loadHOps.put("system:load", "min15", min15.toString());

    }

    public void saveNetworkStats(NetworkStatsResponse response){
        HashOperations<String, String, String> networkHOps = redisTemplate.opsForHash();
        String name = response.name();
        Double recvRatePerSec = response.recvRatePerSec();
        Double sentRatePerSec = response.sentRatePerSec();

        networkHOps.put("system:network", "interface-name", name);
        networkHOps.put("system:network","recvRatePerSec", recvRatePerSec.toString());
        networkHOps.put("system:network","sentRatePerSec", sentRatePerSec.toString());
    }

    public void saveSensorStats(List<SensorStatsResponse> responses){

        SensorStatsResponse response = responses.stream()
                .filter(x -> x.label().equalsIgnoreCase("cpu_thermal 0"))
                .findFirst()
                .orElseThrow();

        HashOperations<String, String, String> sensorHOps = redisTemplate.opsForHash();

        String label = response.label();
        String unit = response.unit();
        Integer value = response.value();
        Integer warning = response.warning();
        Integer critical = response.critical();
        String type = response.type();
        String key = response.key();

        sensorHOps.put("system:sensor", "label", label);
        sensorHOps.put("system:sensor", "unit", unit);
        sensorHOps.put("system:sensor", "value", value.toString());
        //sensorHOps.put("system:sensor", "warning", warning.toString());
        //sensorHOps.put("system:sensor", "critical", critical.toString());
        sensorHOps.put("system:sensor", "type", type);
        sensorHOps.put("system:sensor", "key", key);
    }

    public void saveFileSystemStats(List<FileSystemStatsResponse> responses){

        for (FileSystemStatsResponse response : responses){

            String redisKey = "system:filesystem:" + response.key();

            HashOperations<String, String, String> fsHOps =
                    redisTemplate.opsForHash();

            fsHOps.put(redisKey, "device-name", response.deviceName());
            fsHOps.put(redisKey, "fs-type", response.fsType());
            fsHOps.put(redisKey, "mnt-point", response.mntPoint());
            fsHOps.put(redisKey, "options", response.options());
            fsHOps.put(redisKey, "size", String.valueOf(response.size()));
            fsHOps.put(redisKey, "used", String.valueOf(response.used()));
            fsHOps.put(redisKey, "free", String.valueOf(response.free()));
            fsHOps.put(redisKey, "percent", String.valueOf(response.percent()));
        }
    }

    public void saveUptime(String uptime){
        HashOperations<String, String, String> uptimeHOps = redisTemplate.opsForHash();

        uptimeHOps.put("system:uptime", "value", uptime);
    }

}
