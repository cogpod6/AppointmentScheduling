package com.cts.learning.scheduling.service;

import org.springframework.stereotype.Service;

import com.cts.learning.scheduling.model.AppointmentPO;
import com.cts.learning.scheduling.model.AppointmentPOEO;

@Service
public class AppointmentPOService {
	
	public AppointmentPO mapToModel (AppointmentPOEO appointmentPOEntity) {
		AppointmentPO appointmentPOModel = new AppointmentPO();
		
		return appointmentPOModel;
	}
	
	public AppointmentPOEO mapToEntity (AppointmentPO appointmentPOModel) {
		AppointmentPOEO appointmentPOEntity =  new AppointmentPOEO();
		
		return appointmentPOEntity;
	}

}
