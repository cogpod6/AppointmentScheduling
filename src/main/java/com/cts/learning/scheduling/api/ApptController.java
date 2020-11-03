package com.cts.learning.scheduling.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.learning.scheduling.model.Appointment;
import com.cts.learning.scheduling.service.AppointmentService;
import com.cts.learning.scheduling.util.BusinessException;

@RestController
@RequestMapping("appointment")
public class ApptController {
	
	@Autowired
	AppointmentService appointmentService;
	
	@GetMapping("/{appointmentId}")
	public ResponseEntity<Appointment> getAppointment (@PathVariable Integer appointmentId) throws BusinessException {
		Appointment responseModel = appointmentService.getAppointmentDetails(appointmentId);
		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}
	
	@PostMapping
	public ResponseEntity<Appointment> createAppointment (@RequestBody Appointment appointment) throws BusinessException {
		ResponseEntity<Appointment> response = null;
		Appointment responseModel = appointmentService.createAppointment(appointment);
		if (null != responseModel) {
			response = ResponseEntity.status(HttpStatus.CREATED).body(responseModel) ;
		} else {
			response = ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
		return response;
	}
	
	@PutMapping
	public ResponseEntity<Appointment> updateAppointment (@RequestBody Appointment appointment) throws BusinessException {
		ResponseEntity<Appointment> response = ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		Appointment responseModel = appointmentService.updateAppointment(appointment);
		if (null != responseModel) {
			response = ResponseEntity.status(HttpStatus.OK).body(responseModel) ;
		} 

		return response;
	}
	
	@DeleteMapping("/{appointmentId}")
	public ResponseEntity<Appointment> deleteAppointment (@PathVariable Integer appointmentId) throws BusinessException {
		appointmentService.deleteAppointment(appointmentId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
