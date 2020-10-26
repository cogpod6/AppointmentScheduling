package com.cts.learning.scheduling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.learning.scheduling.repository.PORepo;

@Service
public class POService {
	
	@Autowired
	PORepo poRepository;
	
	public boolean isPOExists (String poNumber) {
		return poRepository.existsById(poNumber);
	}

}
