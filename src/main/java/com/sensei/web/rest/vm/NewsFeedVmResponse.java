package com.sensei.web.rest.vm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class NewsFeedVmResponse {
	
	private NewsFeedVm response; 


	public NewsFeedVmResponse() {
	}
	
	public NewsFeedVmResponse(NewsFeedVm response) {
		super();
		this.response = response;
	}

	public NewsFeedVm getResponse() {
		return response;
	}

//	public void addResponses(NewsFeedVm responses) {
//		this.response = responses;
//	}
	
	public void addResponse(NewsFeedVm response)
	{
		this.response = response;
	}

	@Override
	public String toString() {
		return "NewsFeedVmResponse [response=" + response + "]";
	}
	
}
