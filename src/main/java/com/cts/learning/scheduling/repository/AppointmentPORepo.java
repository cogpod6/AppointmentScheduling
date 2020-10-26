package com.cts.learning.scheduling.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.learning.scheduling.model.AppointmentPOEO;

@Repository
public interface AppointmentPORepo extends JpaRepository<AppointmentPOEO, Integer>{
	
	// void deleteByAppointmentId (Integer appointmentId);
	
	@Query(value = "delete from appointment_po where appointment_id = :id", nativeQuery = true)
	void deleteByAppointmentId(@Param("id") int id);
	
	@Query(value = "select id, appointment_id, po_number from appointment_po where appointment_id = :id", nativeQuery = true)
	List<AppointmentPOEO> getAppointmentPOByApptId (@Param("id") int id);

}
