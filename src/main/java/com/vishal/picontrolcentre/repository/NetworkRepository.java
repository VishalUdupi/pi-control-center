package com.vishal.picontrolcentre.repository;

import com.vishal.picontrolcentre.entity.NetworkHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkRepository extends JpaRepository<NetworkHistory, Long> {
}
