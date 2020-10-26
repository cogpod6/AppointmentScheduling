package com.cts.learning.scheduling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.learning.scheduling.model.DCSlots;
import com.cts.learning.scheduling.model.DCSlotsEO;
import com.cts.learning.scheduling.repository.DCSlotsRepo;

@Service
public class DCSlotService {
	
	@Autowired
	DCService dcService;
	
	@Autowired
	DCSlotsRepo dcSlotsRepo;
	
	public DCSlots getDCSlots (Integer id) {
		return dcSlotsRepo.findById(id).map(dcSlots -> {
			return mapTOModel(dcSlots);
		}).orElseThrow(() -> new RuntimeException("No Slots Found"));
	}
	
	public DCSlots mapTOModel (DCSlotsEO dcSlotsEntity) {
		DCSlots dcSlotsModel = new DCSlots();
		
		dcSlotsModel.setId(dcSlotsEntity.getId());
		dcSlotsModel.setDc(dcService.mapTOModel(dcSlotsEntity.getDc()) );
		dcSlotsModel.setTimeSlots(dcSlotsEntity.getTimeSlots());
		dcSlotsModel.setMaxTrucks(dcSlotsEntity.getMaxTrucks());
		
		return dcSlotsModel;
	}
	
	public DCSlotsEO mapToEntity (DCSlots dcSlotsModel) {
		DCSlotsEO dcSlotsEntity = new DCSlotsEO();
		
		dcSlotsEntity.setId(dcSlotsModel.getId());
		dcSlotsEntity.setDc(dcService.mapToEntity(dcSlotsModel.getDc()) );
		dcSlotsEntity.setTimeSlots(dcSlotsModel.getTimeSlots());
		dcSlotsEntity.setMaxTrucks(dcSlotsModel.getMaxTrucks());
		
		return dcSlotsEntity;
	}

}
