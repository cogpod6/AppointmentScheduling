package com.cts.learning.scheduling.api.test;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.cts.learning.scheduling.api.ApptController;
import com.cts.learning.scheduling.model.Appointment;
import com.cts.learning.scheduling.service.AppointmentService;
import com.cts.learning.scheduling.util.BusinessException;

@RunWith(EasyMockRunner.class)
public class ApptControllerTest {
	
	private ApptController apptController;
	AppointmentService mockAppointmentService;
	
	@Before
	public void setUp () {
		apptController = new ApptController();
		mockAppointmentService = EasyMock.createMock(AppointmentService.class);
				
		ReflectionTestUtils.setField(apptController, "appointmentService", mockAppointmentService);
	}
	
	@Test
	public void getAppointmentTest () throws BusinessException {
		int appointmentId = 1;
		
		EasyMock.expect(mockAppointmentService.getAppointmentDetails(appointmentId)).andReturn(getAppointmentModel()).times(1);
		EasyMock.replay(mockAppointmentService);
		
		ResponseEntity<Appointment> response = apptController.getAppointment(appointmentId);
		Assert.assertEquals(response.getStatusCodeValue(), 200);
	}
	
	@Test
	public void createAppointmentTest () throws BusinessException {
		EasyMock.expect(mockAppointmentService.createAppointment(EasyMock.anyObject(Appointment.class)) ).andReturn(getAppointmentModel()).times(1);
		EasyMock.replay(mockAppointmentService);
		
		ResponseEntity<Appointment> response = apptController.createAppointment(getAppointmentModel());
		Assert.assertEquals(response.getStatusCodeValue(), 201);
	}
	
	@Test
	public void createAppointmentWithConflictTest () throws BusinessException {
		EasyMock.expect(mockAppointmentService.createAppointment(EasyMock.anyObject(Appointment.class))).andReturn(null).times(1);
		EasyMock.replay(mockAppointmentService);
		
		ResponseEntity<Appointment> response = apptController.createAppointment(getAppointmentModel());
		Assert.assertEquals(response.getStatusCodeValue(), 409);
	}
	
	@Test
	public void deleteAppointmentTest () throws BusinessException {
		mockAppointmentService.deleteAppointment(1);
		EasyMock.expectLastCall();
		EasyMock.replay(mockAppointmentService);
	}
	
	@Test
	public void updateAppointmentTest () throws BusinessException {
		EasyMock.expect(mockAppointmentService.updateAppointment(EasyMock.anyObject(Appointment.class)) ).andReturn(getAppointmentModel()).times(1);
		EasyMock.replay(mockAppointmentService);
		
		ResponseEntity<Appointment> response = apptController.updateAppointment(getAppointmentModel());
		Assert.assertEquals(response.getStatusCodeValue(), 200);
	}
	
	@Test
	public void updateAppointmentWithConflictTest () throws BusinessException {
		EasyMock.expect(mockAppointmentService.updateAppointment(EasyMock.anyObject(Appointment.class))).andReturn(null).times(1);
		EasyMock.replay(mockAppointmentService);
		
		ResponseEntity<Appointment> response = apptController.updateAppointment(getAppointmentModel());
		Assert.assertEquals(response.getStatusCodeValue(), 409);
	}
	
	public Appointment getAppointmentModel() {
		Appointment appointmentModel = new Appointment();
		appointmentModel.setDate("2020-11-03");
		appointmentModel.setDcNumber("7030");
		appointmentModel.setId(1);
		
		return appointmentModel;
	}

}
