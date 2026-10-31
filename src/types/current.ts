export interface CpuStats {
    cores: number;
    total: number;
    user: number;
    iowait: number;
}

export interface MemoryStats {
    total: number;
    free: number;
    percent: number;
    used: number;
}

export interface LoadStats {
    min1: number;
    min5: number;
    min15: number;
}

export interface NetworkStats {
    name: string;
    recvRatePerSec: number;
    sentRatePerSec: number;
}

export interface FileSystemStats {
    deviceName: string;
    mntPoint: string;
    size: number;
    used: number;
    free: number;
    percent: number;
}

export interface SensorStats {
    label: string;
    unit: string;
    value: number;
    type: string;
    key: string;
}