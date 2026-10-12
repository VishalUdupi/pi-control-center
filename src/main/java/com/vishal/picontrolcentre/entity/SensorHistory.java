package com.vishal.picontrolcentre.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sensor_history")
public class SensorHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Instant recordedAt;
    private String label;
    private String unit;
    private int value;

    @Column(name = "sensor_type")
    private String type;

    @Column(name = "sensor_key")
    private String key;

    public SensorHistory(
            String label,
            String unit,
            int value,
            String type,
            String key) {
        this.label = label;
        this.unit = unit;
        this.value = value;
        this.type = type;
        this.key = key;
    }

    protected SensorHistory() {
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

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
}

