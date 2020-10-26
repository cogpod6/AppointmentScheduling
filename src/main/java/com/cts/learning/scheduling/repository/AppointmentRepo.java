package com.cts.learning.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.learning.scheduling.model.AppointmentEO;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEO, Integer> {
	
	@Query(value = "select count(*) from appointment where slot_id = :id", nativeQuery = true)
	int getCountBySlotId(@Param("id") int id);

}
