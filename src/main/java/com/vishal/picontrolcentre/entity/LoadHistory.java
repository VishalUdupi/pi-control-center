package com.vishal.picontrolcentre.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "load_history")
public class LoadHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Instant recordedAt;
    private double min1;
    private double min5;
    private double min15;

    public LoadHistory(double min1, double min5, double min15) {
        this.min1 = min1;
        this.min5 = min5;
        this.min15 = min15;
    }

    protected LoadHistory() {
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

    public double getMin1() { return min1; }
    public void setMin1(double min1) { this.min1 = min1; }

    public double getMin5() { return min5; }
    public void setMin5(double min5) { this.min5 = min5; }

    public double getMin15() { return min15; }
    public void setMin15(double min15) { this.min15 = min15; }
}

