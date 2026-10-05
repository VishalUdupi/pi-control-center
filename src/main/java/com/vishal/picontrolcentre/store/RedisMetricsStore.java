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

    public CpuStatsResponse getCpuStats(){
        HashOperations<String, String, String> cpuHOps = redisTemplate.opsForHash();
        String cores = cpuHOps.get("system:cpu", "cores");
        String iowait = cpuHOps.get("system:cpu", "iowait");
        String total = cpuHOps.get("system:cpu", "total");
        String user = cpuHOps.get("system:cpu", "user");

        return new CpuStatsResponse(
                Integer.parseInt(cores),
                Double.parseDouble(total),
                Double.parseDouble(user),
                Double.parseDouble(iowait)
        );

    }

    public void saveMemoryStats(MemoryStatsResponse response){
        HashOperations<String, String, String> memoryHOps = redisTemplate.opsForHash();
        Long free = response.free();
        Double percent = response.percent();
        Long total = response.total();
        Long used = response.used();

        memoryHOps.put("system:memory", "total", total.toString());
        memoryHOps.put("system:memory", "used", used.toString());
        memoryHOps.put("system:memory", "percent", percent.toString());
        memoryHOps.put("system:memory", "free", free.toString());


    }

    public MemoryStatsResponse getMemoryStats() {
        HashOperations<String, String, String> memoryHOps =
                redisTemplate.opsForHash();

        String total = memoryHOps.get("system:memory", "total");
        String used = memoryHOps.get("system:memory", "used");
        String free = memoryHOps.get("system:memory", "free");
        String percent = memoryHOps.get("system:memory", "percent");

        return new MemoryStatsResponse(
                Long.parseLong(total),
                Long.parseLong(free),
                Double.parseDouble(percent),
                Long.parseLong(used)
        );
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

    public LoadStatsResponse getLoadStats() {
        HashOperations<String, String, String> loadHOps =
                redisTemplate.opsForHash();

        String min1 = loadHOps.get("system:load", "min1");
        String min5 = loadHOps.get("system:load", "min5");
        String min15 = loadHOps.get("system:load", "min15");

        return new LoadStatsResponse(
                Double.parseDouble(min1),
                Double.parseDouble(min5),
                Double.parseDouble(min15)
        );
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

    public NetworkStatsResponse getNetworkStats() {
        HashOperations<String, String, String> networkHOps =
                redisTemplate.opsForHash();

        String name =
                networkHOps.get("system:network", "interface-name");

        String recvRatePerSec =
                networkHOps.get("system:network", "recvRatePerSec");

        String sentRatePerSec =
                networkHOps.get("system:network", "sentRatePerSec");

        return new NetworkStatsResponse(
                name,
                Double.parseDouble(recvRatePerSec),
                Double.parseDouble(sentRatePerSec)
        );
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
        String type = response.type();
        String key = response.key();

        sensorHOps.put("system:sensor", "label", label);
        sensorHOps.put("system:sensor", "unit", unit);
        sensorHOps.put("system:sensor", "value", value.toString());
        sensorHOps.put("system:sensor", "type", type);
        sensorHOps.put("system:sensor", "key", key);
    }

    public SensorStatsResponse getSensorStats() {
        HashOperations<String, String, String> sensorHOps =
                redisTemplate.opsForHash();

        String label = sensorHOps.get("system:sensor", "label");
        String unit = sensorHOps.get("system:sensor", "unit");
        String value = sensorHOps.get("system:sensor", "value");
        String type = sensorHOps.get("system:sensor", "type");
        String key = sensorHOps.get("system:sensor", "key");

        return new SensorStatsResponse(
                label,
                unit,
                Integer.parseInt(value),
                type,
                key
        );
    }


    public void saveFileSystemStats(List<FileSystemStatsResponse> responses){

        for (FileSystemStatsResponse response : responses){
            if(response.mntPoint().equalsIgnoreCase("/etc/hostname")) {

                String redisKey = "system:filesystem:" + response.deviceName();

                HashOperations<String, String, String> fsHOps =
                        redisTemplate.opsForHash();

                fsHOps.put(redisKey, "device-name", response.deviceName());
                fsHOps.put(redisKey, "size", String.valueOf(response.size()));
                fsHOps.put(redisKey, "used", String.valueOf(response.used()));
                fsHOps.put(redisKey, "free", String.valueOf(response.free()));
                fsHOps.put(redisKey, "percent", String.valueOf(response.percent()));
            }
        }
    }

    public FileSystemStatsResponse getFileSystemStats(String deviceName) {
        String redisKey = "system:filesystem:" + deviceName;

        HashOperations<String, String, String> fsHOps =
                redisTemplate.opsForHash();

        String mntPoint = "/etc/hostname";
        String device = fsHOps.get(redisKey, "device-name");
        String size = fsHOps.get(redisKey, "size");
        String used = fsHOps.get(redisKey, "used");
        String free = fsHOps.get(redisKey, "free");
        String percent = fsHOps.get(redisKey, "percent");

        return new FileSystemStatsResponse(
                device,
                mntPoint,
                Long.parseLong(size),
                Long.parseLong(used),
                Long.parseLong(free),
                Double.parseDouble(percent)
        );
    }

    public void saveUptime(String uptime){
        HashOperations<String, String, String> uptimeHOps = redisTemplate.opsForHash();

        uptimeHOps.put("system:uptime", "value", uptime);
    }

    public String getUptime() {
        HashOperations<String, String, String> uptimeHOps =
                redisTemplate.opsForHash();

        return uptimeHOps.get("system:uptime", "value");
    }

}
