package com.vishal.picontrolcentre.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "memory_history")
public class MemoryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Instant recordedAt;
    private long total;
    private long free;
    private double percent;
    private long used;

    public MemoryHistory(long total, long free, double percent, long used) {
        this.total = total;
        this.free = free;
        this.percent = percent;
        this.used = used;
    }

    protected MemoryHistory() {
    }

    @PrePersist
    protected void setRecordedAtBeforeInsert() {
        if (recordedAt == null) {
            recordedAt = Instant.now();
        }
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Instant getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Instant recordedAt) { this.recordedAt = recordedAt; }

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }

    public long getFree() { return free; }
    public void setFree(long free) { this.free = free; }

    public double getPercent() { return percent; }
    public void setPercent(double percent) { this.percent = percent; }

    public long getUsed() { return used; }
    public void setUsed(long used) { this.used = used; }
}
