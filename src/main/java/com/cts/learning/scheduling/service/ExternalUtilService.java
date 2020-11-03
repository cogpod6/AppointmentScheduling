package com.cts.learning.scheduling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cts.learning.scheduling.model.DownstreamApp;
import com.cts.learning.scheduling.model.PO;

public class ExternalUtilService {
	
	@Autowired
	RestTemplate restTemplate;
	
	public boolean sendDownstreamMessage (DownstreamApp downstreamApp) {
		String url = "http://send-sch-info/publish";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<DownstreamApp> requestEntity = new HttpEntity<>(downstreamApp, headers);

		ResponseEntity<String> response = restTemplate.exchange( url, HttpMethod.POST, requestEntity , String.class );
		System.out.println("status: " + response.getBody());
		return true;
	}
	
	public PO getPO (String poNumber) {
		String url = String.format("http://po-download/po/%s", poNumber);
		ResponseEntity<PO> poResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<PO>() {
		});
		return poResponse.getBody();
	}

}
