package com.sensei.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ActivateUserVm 
{
	@NotNull
   // @Size(min = 6, max = 6)
	private String key;

	public String getKey() {
		return key;
	}

	public ActivateUserVm()
	{
	}
	
	public ActivateUserVm(String key)
	{
		super();
		this.key = key;
	}
	
	public void setKey(String key) 
	{
		this.key = key;
	}

	@Override
	public String toString() {
		return "ActivateUserVm [key=" + key + "]";
	}
	
	
}
