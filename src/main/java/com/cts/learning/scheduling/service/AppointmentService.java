package com.cts.learning.scheduling.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cts.learning.scheduling.model.Appointment;
import com.cts.learning.scheduling.model.AppointmentEO;
import com.cts.learning.scheduling.model.AppointmentPO;
import com.cts.learning.scheduling.model.AppointmentPOEO;
import com.cts.learning.scheduling.model.DCSlots;
import com.cts.learning.scheduling.model.DownstreamApp;
import com.cts.learning.scheduling.model.PO;
import com.cts.learning.scheduling.repository.AppointmentPORepo;
import com.cts.learning.scheduling.repository.AppointmentRepo;

@Service
public class AppointmentService {
	
	@Autowired
	AppointmentRepo appointmentRepo;
	
	@Autowired
    AppointmentPORepo appointmentPORepo;
	
	@Autowired
	DCService dcService;
	
	@Autowired
	DCSlotService dcSlotService;
	
	@Autowired
	TruckService truckService;
	
	@Autowired
	POService poService;
	
	@Autowired
	RestTemplate restTemplate;
	
	public Appointment createAppointment (Appointment appointmentModel) throws RuntimeException {
		int usedTruckCount = appointmentRepo.getCountBySlotId(appointmentModel.getDcSlots().getId());
		int availableSlotCount = availableSlotCount(appointmentModel.getDcSlots().getId());
		
		// check whether the po is present
		if (isPOAvailable(appointmentModel.getAppointmentPOs()) ) {
			// check whether the slot count is reached
			if (usedTruckCount < availableSlotCount) {
				AppointmentEO appointmentEntity = mapToEntity(appointmentModel);

				AppointmentEO responseEntity = appointmentRepo.save(appointmentEntity);

				List<String> availablePOs = new ArrayList<>();
				List<AppointmentPOEO> appointmentPOEntityList = new ArrayList<>(); 
				for (AppointmentPO appointmentPO : appointmentModel.getAppointmentPOs()) {
					AppointmentPOEO appointmentPOEntity = new AppointmentPOEO();

					appointmentPOEntity.setId(appointmentPO.getId());
					appointmentPOEntity.setPoNumber(appointmentPO.getPoNumber());
					appointmentPOEntity.setAppointment(responseEntity);

					appointmentPOEntityList.add(appointmentPOEntity);
					availablePOs.add(appointmentPO.getPoNumber());
				}

				Iterable<AppointmentPOEO> iterable = () -> new Iterator<AppointmentPOEO>() {
					private int index = 0;

					@Override
					public boolean hasNext() {
						return appointmentPOEntityList.size() > index;
					}

					@Override
					public AppointmentPOEO next() {
						return appointmentPOEntityList.get(index++);
					}
				};

				appointmentPORepo.saveAll(iterable);
				
				// send downstream messages
				DownstreamApp downstreamApp = new DownstreamApp();
				downstreamApp.setAppointmentId(responseEntity.getId());
				downstreamApp.setTruckNumber(appointmentModel.getTruck().getTruckNumber());
				downstreamApp.setDcNumber(appointmentModel.getDcNumber());
				downstreamApp.setTimeSlot(appointmentModel.getDcSlots().getTimeSlots());
				downstreamApp.setPos(availablePOs);
				
				sendDownstreamMessage(downstreamApp);
				return mapToModel(responseEntity);
			} else {
				throw new RuntimeException("Max truck count reached for the slot");
			}
		} else {
			throw new RuntimeException("PO is not available to create appointment");
		}
	}
	
	public int availableSlotCount (int slotId) {
		int slotCount = 0;
		try {
			DCSlots dcSlots = dcSlotService.getDCSlots(slotId);
			slotCount = dcSlots.getMaxTrucks();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return slotCount;
	}
	
	public Boolean isPOAvailable (List<AppointmentPO> appointmentPOs) {
		for (AppointmentPO appointmentPO : appointmentPOs) {
			/* if (!poService.isPOExists(appointmentPO.getPoNumber()) ) {
				return false;
			} */
			PO po = getPO(appointmentPO.getPoNumber() );
			if (null != po) {
				return true;
			}
		}
		return false;
	}
	
	public void sendDownstreamMessage (DownstreamApp downstreamApp) {
		String url = "http://send-sch-info/publish";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<DownstreamApp> requestEntity = new HttpEntity<>(downstreamApp, headers);

		ResponseEntity<String> response = restTemplate.exchange( url, HttpMethod.POST, requestEntity , String.class );
		System.out.println("status: " + response.getBody());
	}
	
	public PO getPO (String poNumber) {
		String url = String.format("http://po-download/po/%s", poNumber);
		ResponseEntity<PO> poResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<PO>() {
		});
		return poResponse.getBody();
		
	}
	
	@Transactional
	public Appointment updateAppointment (Appointment appointmentModel) throws RuntimeException {
		int usedTruckCount = appointmentRepo.getCountBySlotId(appointmentModel.getDcSlots().getId());
		int availableSlotCount = availableSlotCount(appointmentModel.getDcSlots().getId());

		AppointmentEO responseEntity = null;
		// check whether the po is present
		if (isPOAvailable(appointmentModel.getAppointmentPOs()) ) {
			// check whether the slot count is reached
			if (usedTruckCount < availableSlotCount) {
				if (appointmentRepo.findById(appointmentModel.getId()).isPresent() ) {
					responseEntity = appointmentRepo.save(mapToEntity(appointmentModel) );

					// delete all appointment with po number
					List<AppointmentPOEO> appointmentPOs = appointmentPORepo.getAppointmentPOByApptId(appointmentModel.getId());
					for (AppointmentPOEO appointmentPO : appointmentPOs) {
						appointmentPORepo.deleteById(appointmentPO.getId());
					}

					for (AppointmentPO appointmentPO : appointmentModel.getAppointmentPOs()) {
						AppointmentPOEO appointmentPOEntity = new AppointmentPOEO();

						appointmentPOEntity.setId(appointmentPO.getId());
						appointmentPOEntity.setPoNumber(appointmentPO.getPoNumber());
						appointmentPOEntity.setAppointment(responseEntity);

						appointmentPORepo.save(appointmentPOEntity);
					} 
				} else {
					throw new RuntimeException("No Appointments found to update");
				}
			} else {
				throw new RuntimeException("Slots were reached with max truck count");
			}
		} else {
			throw new RuntimeException("One of the po is not available to update the appointment");
		}

		return  null != responseEntity ? mapToModel(responseEntity) : null;
	}
	
	public void deleteAppointment (Integer appointmentId) throws RuntimeException {
		Optional<AppointmentEO> appointmentEO = appointmentRepo.findById(appointmentId);
		if (appointmentEO.isPresent() ) {
			List<AppointmentPOEO> appointmentPOs = appointmentPORepo.getAppointmentPOByApptId(appointmentId);
			for (AppointmentPOEO appointmentPO : appointmentPOs) {
				appointmentPORepo.deleteById(appointmentPO.getId());
			}
			
			appointmentRepo.delete(appointmentEO.get());
		} else {
			throw new RuntimeException("No Appointment Found");
		}
	}
	
	public Appointment getAppointmentDetails (Integer id) throws RuntimeException {
		return appointmentRepo.findById(id).map(appointment -> {
			return mapToModel(appointment);
		}).orElseThrow(() -> new RuntimeException("No Appointment Found"));
	}
	
	public AppointmentEO mapToEntity (Appointment appointmentModel) {
		AppointmentEO appointmentEntity =  new AppointmentEO();
		
		appointmentEntity.setId(appointmentModel.getId());
		appointmentEntity.setDcNumber(appointmentModel.getDcNumber());
		appointmentEntity.setDcSlots(dcSlotService.mapToEntity(appointmentModel.getDcSlots()) );
		appointmentEntity.setTruck(truckService.mapToEntity(appointmentModel.getTruck()) );
		try {
			appointmentEntity.setAppointmentDate(new SimpleDateFormat("yyyy-MM-dd").parse(appointmentModel.getDate()) );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return appointmentEntity;
	}
	
	public Appointment mapToModel (AppointmentEO appointmentEntity) {
		Appointment appointmentModel = new Appointment();
		
		appointmentModel.setId(appointmentEntity.getId());
		appointmentModel.setDcNumber(appointmentEntity.getDcNumber());
		appointmentModel.setDcSlots(dcSlotService.mapTOModel(appointmentEntity.getDcSlots()) );
		appointmentModel.setTruck(truckService.mapToModel(appointmentEntity.getTruck()) );
		appointmentModel.setDate(new SimpleDateFormat("yyyy-MM-dd").format(appointmentEntity.getAppointmentDate()) );
		
		return appointmentModel;
	}
	
}
