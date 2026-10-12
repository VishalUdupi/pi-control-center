package com.vishal.picontrolcentre.repository;

import com.vishal.picontrolcentre.entity.MemoryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryRepository extends JpaRepository<MemoryHistory, Long> {
}
