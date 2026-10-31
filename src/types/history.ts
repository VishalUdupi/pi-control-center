export interface CpuHistory {
    id: number;
    recordedAt: string;
    cores: number;
    total: number;
    user: number;
    iowait: number;
}

export interface MemoryHistory {
    id: number;
    recordedAt: string;
    total: number;
    free: number;
    percent: number;
    used: number;
}

export interface LoadHistory {
    id: number;
    recordedAt: string;
    min1: number;
    min5: number;
    min15: number;
}

export interface NetworkHistory {
    id: number;
    recordedAt: string;
    name: string;
    recvRatePerSec: number;
    sentRatePerSec: number;
}

export interface SensorHistory {
    id: number;
    recordedAt: string;
    label: string;
    unit: string;
    value: number;
    type: string;
    key: string;
}