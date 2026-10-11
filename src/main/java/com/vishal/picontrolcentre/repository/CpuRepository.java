package com.vishal.picontrolcentre.repository;

import com.vishal.picontrolcentre.entity.CpuHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuRepository extends JpaRepository<CpuHistory, Long> {

}
