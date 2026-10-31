package com.vishal.picontrolcentre.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "cpu_history")
public class CpuHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Instant recordedAt;
    private int cores;
    private Double total;
    @Column(name = "user_percent")
    private Double user;
    private Double iowait;

    public CpuHistory(int cores, Double total, Double user, Double iowait) {
        this.cores = cores;
        this.total = total;
        this.user = user;
        this.iowait = iowait;
    }

    protected CpuHistory() {

    }

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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getUser() {
        return user;
    }

    public void setUser(Double user) {
        this.user = user;
    }

    public Double getIowait() {
        return iowait;
    }

    public void setIowait(Double iowait) {
        this.iowait = iowait;
    }
}
