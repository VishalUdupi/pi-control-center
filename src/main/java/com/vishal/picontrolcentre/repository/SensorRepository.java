package com.vishal.picontrolcentre.repository;

import com.vishal.picontrolcentre.entity.SensorHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<SensorHistory, Long> {
}
