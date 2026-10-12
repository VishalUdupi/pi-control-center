package com.vishal.picontrolcentre.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "network_history")
public class NetworkHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Instant recordedAt;
    private String name;
    private double recvRatePerSec;
    private double sentRatePerSec;

    public NetworkHistory(
            String name,
            double recvRatePerSec,
            double sentRatePerSec) {
        this.name = name;
        this.recvRatePerSec = recvRatePerSec;
        this.sentRatePerSec = sentRatePerSec;
    }

    protected NetworkHistory() {
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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getRecvRatePerSec() { return recvRatePerSec; }
    public void setRecvRatePerSec(double recvRatePerSec) {
        this.recvRatePerSec = recvRatePerSec;
    }

    public double getSentRatePerSec() { return sentRatePerSec; }
    public void setSentRatePerSec(double sentRatePerSec) {
        this.sentRatePerSec = sentRatePerSec;
    }
}

