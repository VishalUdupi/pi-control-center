package com.vishal.picontrolcentre.repository;

import com.vishal.picontrolcentre.entity.LoadHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadRepository extends JpaRepository<LoadHistory, Long> {
}
