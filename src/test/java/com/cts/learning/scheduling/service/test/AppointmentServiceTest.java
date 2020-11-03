package com.cts.learning.scheduling.service.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.util.ReflectionTestUtils;

import com.cts.learning.scheduling.model.Appointment;
import com.cts.learning.scheduling.model.AppointmentEO;
import com.cts.learning.scheduling.model.AppointmentPO;
import com.cts.learning.scheduling.model.AppointmentPOEO;
import com.cts.learning.scheduling.model.DCSlots;
import com.cts.learning.scheduling.model.DCSlotsEO;
import com.cts.learning.scheduling.model.DownstreamApp;
import com.cts.learning.scheduling.model.PO;
import com.cts.learning.scheduling.model.Truck;
import com.cts.learning.scheduling.model.TruckEO;
import com.cts.learning.scheduling.model.TruckType;
import com.cts.learning.scheduling.model.TruckTypeEO;
import com.cts.learning.scheduling.repository.AppointmentPORepo;
import com.cts.learning.scheduling.repository.AppointmentRepo;
import com.cts.learning.scheduling.service.AppointmentService;
import com.cts.learning.scheduling.service.DCSlotService;
import com.cts.learning.scheduling.service.ExternalUtilService;
import com.cts.learning.scheduling.service.TruckService;
import com.cts.learning.scheduling.util.BusinessException;

@RunWith(EasyMockRunner.class)
public class AppointmentServiceTest {
	
	private AppointmentService appointmentService;
	private AppointmentRepo mockAppointmentRepo;
    private AppointmentPORepo mockAppointmentPORepo;
	private DCSlotService mockDcSlotService;
	private TruckService mockTruckService;
	private ExternalUtilService mockExternalService;
	
	@Before
	public void setUp () {
		appointmentService = new AppointmentService();
		mockAppointmentRepo = EasyMock.createMock(AppointmentRepo.class);
		mockAppointmentPORepo = EasyMock.createMock(AppointmentPORepo.class);
		mockDcSlotService = EasyMock.createMock(DCSlotService.class);
		mockTruckService = EasyMock.createMock(TruckService.class);
		mockExternalService = EasyMock.createMock(ExternalUtilService.class);
				
		ReflectionTestUtils.setField(appointmentService, "appointmentRepo", mockAppointmentRepo);
		ReflectionTestUtils.setField(appointmentService, "appointmentPORepo", mockAppointmentPORepo);
		ReflectionTestUtils.setField(appointmentService, "dcSlotService", mockDcSlotService);
		ReflectionTestUtils.setField(appointmentService, "truckService", mockTruckService);
		ReflectionTestUtils.setField(appointmentService, "externalService", mockExternalService);
	}
	
	@Test
	public void createAppointmentTest() throws BusinessException {
		int dcSlotId = 1;
		String poNUmber = "1111111111";
		
		DCSlots dcSlots = new DCSlots();
		dcSlots.setId(1);
		dcSlots.setMaxTrucks(10);
		dcSlots.setTimeSlots("07:00 - 08:00");
		
		DCSlotsEO dcSlotsEntity = new DCSlotsEO();
		dcSlotsEntity.setId(dcSlots.getId());
		dcSlotsEntity.setMaxTrucks(dcSlots.getMaxTrucks());
		dcSlotsEntity.setTimeSlots(dcSlots.getTimeSlots());
		
		TruckType truckTypeModel = new TruckType();
		truckTypeModel.setId(1);
		truckTypeModel.setType("test");
		
		TruckTypeEO truckType = new TruckTypeEO();
		truckType.setId(truckTypeModel.getId());
		truckType.setTruckType(truckTypeModel.getType());
		
		Truck truckModel = new Truck();
		truckModel.setId(1);
		truckModel.setTruckName("walmart");
		truckModel.setTruckNumber("1234");
		truckModel.setTruckType(truckTypeModel);
		
		TruckEO truck = new TruckEO();
		truck.setId(truckModel.getId());
		truck.setTruckName(truckModel.getTruckName());
		truck.setTruckNumber(truckModel.getTruckNumber());
		truck.setTruckType(truckType);
		
		AppointmentPO appointmentPO = new AppointmentPO();
		appointmentPO.setPoNumber(poNUmber);
		List<AppointmentPO> appointmentPOList = new ArrayList<>();
		appointmentPOList.add(appointmentPO);
		
		Appointment appointmentModel = new Appointment();
		appointmentModel.setDate("2020-11-03");
		appointmentModel.setDcNumber("7030");
		appointmentModel.setDcSlots(dcSlots);
		appointmentModel.setAppointmentPOs(appointmentPOList);
		appointmentModel.setTruck(truckModel);
		
		PO po = new PO();
		po.setPoNumber(poNUmber);
		po.setOrderQuantity(20);
		po.setPoDate(new Date());
		
		AppointmentEO appointmentEntity =  new AppointmentEO();

		appointmentEntity.setDcNumber(appointmentModel.getDcNumber());
		try {
			appointmentEntity.setAppointmentDate(new SimpleDateFormat("yyyy-MM-dd").parse(appointmentModel.getDate()) );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		appointmentEntity.setDcSlots(dcSlotsEntity);
		appointmentEntity.setTruck(truck);
		
		AppointmentPOEO appointmentPOEO = new AppointmentPOEO();
		appointmentPOEO.setAppointment(appointmentEntity);
		appointmentPOEO.setId(1);
		appointmentPOEO.setPoNumber(poNUmber);
		
		EasyMock.expect(mockAppointmentRepo.getCountBySlotId(dcSlotId)).andReturn(5).times(1);
		
		EasyMock.expect(mockDcSlotService.getDCSlots(1)).andReturn(dcSlots).times(1);
		EasyMock.expect(mockDcSlotService.mapToEntity(dcSlots)).andReturn(dcSlotsEntity).times(1);
		EasyMock.expect(mockDcSlotService.mapTOModel(dcSlotsEntity)).andReturn(dcSlots).times(1);
		EasyMock.replay(mockDcSlotService);
		
		EasyMock.expect(mockTruckService.mapToEntity(truckModel)).andReturn(truck).times(1);
		EasyMock.expect(mockTruckService.mapToModel(truck)).andReturn(truckModel).times(1);
		EasyMock.replay(mockTruckService);
		
		EasyMock.expect(mockExternalService.getPO(poNUmber)).andReturn(po).times(1);
		
		EasyMock.expect(mockAppointmentRepo.save(EasyMock.anyObject(AppointmentEO.class))).andReturn(appointmentEntity).times(1);
		EasyMock.replay(mockAppointmentRepo);
		
		EasyMock.expect(mockAppointmentPORepo.save(EasyMock.anyObject(AppointmentPOEO.class))).andReturn(appointmentPOEO).times(1);
		EasyMock.replay(mockAppointmentPORepo);
		
		List<String> availablePOs = new ArrayList<>();
		availablePOs.add(poNUmber);
		
		DownstreamApp downstreamApp = new DownstreamApp();
		downstreamApp.setAppointmentId(137);
		downstreamApp.setTruckNumber(appointmentModel.getTruck().getTruckNumber());
		downstreamApp.setDcNumber(appointmentModel.getDcNumber());
		downstreamApp.setTimeSlot(appointmentModel.getDcSlots().getTimeSlots());
		downstreamApp.setPos(availablePOs);
		
		EasyMock.expect(mockExternalService.sendDownstreamMessage(EasyMock.anyObject(DownstreamApp.class))).andReturn(true).times(1);
		/*mockExternalService.sendDownstreamMessage(downstreamApp);
		EasyMock.expectLastCall(); */
		EasyMock.replay(mockExternalService);
		
		 appointmentService.createAppointment(appointmentModel);
	}
	
	/*@Test
	public void updateAppointmentTest() {
		
	} */
	
	@Test
	public void deleteAppointmentTest () throws BusinessException {
		AppointmentEO appointmentEntity = getAppointmentEO();
		Optional<AppointmentEO> op = Optional.of(appointmentEntity);
		
		AppointmentPOEO appointmentPOEntity = getAppointmentPOEntity(appointmentEntity);
		List<AppointmentPOEO> appointmentPOList = new ArrayList<>();
		appointmentPOList.add(appointmentPOEntity);
		
		EasyMock.expect(mockAppointmentRepo.findById(1)).andReturn(op).times(1);
		
		EasyMock.expect(mockAppointmentPORepo.getAppointmentPOByApptId(1)).andReturn(appointmentPOList).times(1);
		
		mockAppointmentPORepo.deleteById(1);
		EasyMock.expectLastCall();
		EasyMock.replay(mockAppointmentPORepo);
		
		mockAppointmentRepo.delete(appointmentEntity);
		EasyMock.expectLastCall();
		EasyMock.replay(mockAppointmentRepo);
		
		appointmentService.deleteAppointment(1);
	}
	
	@Test
	public void getAppointmentsTest() throws BusinessException {
		int appointmentId = 1;
		AppointmentEO appointmentEntity = getAppointmentEO();
		Optional<AppointmentEO> op = Optional.of(appointmentEntity);
		
		EasyMock.expect(mockAppointmentRepo.findById(appointmentId)).andReturn(op).times(1);
		EasyMock.replay(mockAppointmentRepo);
		
		appointmentService.getAppointmentDetails(appointmentId);
		EasyMock.verify(mockAppointmentRepo);
	}
	
	/*
	public DCSlots getDCSlotsModel () {
		
	}
	
	public DCSlotsEO getDCSlotsEntity () {
		
	}
	
	public Truck getTruckModel () {
		
	}
	
	public TruckEO getTruckEntity () {
		
	}
	
	public Appointment getAppointmentModel () {
		
	} */
	
	public AppointmentEO getAppointmentEO () {
		DCSlots dcSlots = new DCSlots();
		dcSlots.setId(1);
		dcSlots.setMaxTrucks(10);
		dcSlots.setTimeSlots("07:00 - 08:00");
		
		DCSlotsEO dcSlotsEntity = new DCSlotsEO();
		dcSlotsEntity.setId(dcSlots.getId());
		dcSlotsEntity.setMaxTrucks(dcSlots.getMaxTrucks());
		dcSlotsEntity.setTimeSlots(dcSlots.getTimeSlots());
		
		TruckType truckTypeModel = new TruckType();
		truckTypeModel.setId(1);
		truckTypeModel.setType("test");
		
		TruckTypeEO truckType = new TruckTypeEO();
		truckType.setId(truckTypeModel.getId());
		truckType.setTruckType(truckTypeModel.getType());
		
		Truck truckModel = new Truck();
		truckModel.setId(1);
		truckModel.setTruckName("walmart");
		truckModel.setTruckNumber("1234");
		truckModel.setTruckType(truckTypeModel);
		
		TruckEO truck = new TruckEO();
		truck.setId(truckModel.getId());
		truck.setTruckName(truckModel.getTruckName());
		truck.setTruckNumber(truckModel.getTruckNumber());
		truck.setTruckType(truckType);
		
		AppointmentPO appointmentPO = new AppointmentPO();
		appointmentPO.setPoNumber("1111111111");
		List<AppointmentPO> appointmentPOList = new ArrayList<>();
		appointmentPOList.add(appointmentPO);
		
		Appointment appointmentModel = new Appointment();
		appointmentModel.setDate("2020-11-03");
		appointmentModel.setDcNumber("7030");
		appointmentModel.setDcSlots(dcSlots);
		appointmentModel.setAppointmentPOs(appointmentPOList);
		appointmentModel.setTruck(truckModel);
		
		PO po = new PO();
		po.setPoNumber("1111111111");
		po.setOrderQuantity(20);
		po.setPoDate(new Date());
		
		AppointmentEO appointmentEntity =  new AppointmentEO();

		appointmentEntity.setDcNumber(appointmentModel.getDcNumber());
		try {
			appointmentEntity.setAppointmentDate(new SimpleDateFormat("yyyy-MM-dd").parse(appointmentModel.getDate()) );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		appointmentEntity.setDcSlots(dcSlotsEntity);
		appointmentEntity.setTruck(truck);
		
		return appointmentEntity;
	}
	
	public AppointmentPOEO getAppointmentPOEntity (AppointmentEO appointmentEntity) {
		AppointmentPOEO appointmentPOEO = new AppointmentPOEO();
		appointmentPOEO.setAppointment(appointmentEntity);
		appointmentPOEO.setId(1);
		appointmentPOEO.setPoNumber("1111111111");
		
		return appointmentPOEO;
	}

}
