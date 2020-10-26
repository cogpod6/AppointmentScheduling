package com.cts.learning.scheduling.service;

import org.springframework.stereotype.Service;

import com.cts.learning.scheduling.model.Truck;
import com.cts.learning.scheduling.model.TruckEO;
import com.cts.learning.scheduling.model.TruckType;
import com.cts.learning.scheduling.model.TruckTypeEO;

@Service
public class TruckService {

	public Truck mapToModel (TruckEO truckEntity) {
		Truck truckModel = new Truck();
		
		truckModel.setId(truckEntity.getId());
		truckModel.setTruckNumber(truckEntity.getTruckNumber());
		truckModel.setTruckName(truckEntity.getTruckName());
		
		TruckType truckTypeModel = new TruckType();
		truckTypeModel.setId (truckEntity.getTruckType().getId());
		truckTypeModel.setType(truckEntity.getTruckType().getTruckType());
		
		truckModel.setTruckType(truckTypeModel);
		
		return truckModel;
	}
	
	public TruckEO mapToEntity (Truck truckModel) {
		TruckEO truckEntity = new TruckEO();
		
		truckEntity.setId(truckModel.getId());
		truckEntity.setTruckNumber(truckModel.getTruckNumber());
		truckEntity.setTruckName(truckModel.getTruckName());
		 
		TruckTypeEO truckTypeEntity = new TruckTypeEO();
		truckTypeEntity.setId (truckModel.getTruckType().getId());
		truckTypeEntity.setTruckType(truckModel.getTruckType().getType());
		
		truckEntity.setTruckType(truckTypeEntity);
		
		return truckEntity;
	}
	
}
