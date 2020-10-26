package com.cts.learning.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.learning.scheduling.model.POEO;

@Repository
public interface PORepo extends JpaRepository<POEO, String> {

}
