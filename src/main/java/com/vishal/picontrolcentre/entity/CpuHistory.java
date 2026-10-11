package com.vishal.picontrolcentre.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "cpu_history")
public class CpuHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Instant recordedAt;
    private int cores;
    private BigDecimal total;
    private BigDecimal user;
    private BigDecimal iowait;

    @PrePersist
    protected void recordedAt(){
        if(recordedAt == null){
            recordedAt = Instant.now();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Instant recordedAt) {
        this.recordedAt = recordedAt;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getUser() {
        return user;
    }

    public void setUser(BigDecimal user) {
        this.user = user;
    }

    public BigDecimal getIowait() {
        return iowait;
    }

    public void setIowait(BigDecimal iowait) {
        this.iowait = iowait;
    }
}
