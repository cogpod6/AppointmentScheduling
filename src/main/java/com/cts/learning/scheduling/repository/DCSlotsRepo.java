package com.cts.learning.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.learning.scheduling.model.DCSlotsEO;

@Repository
public interface DCSlotsRepo extends JpaRepository<DCSlotsEO, Integer>{

}
