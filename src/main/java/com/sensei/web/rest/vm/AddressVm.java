package com.sensei.web.rest.vm;

import javax.validation.constraints.NotNull;

public class AddressVm {

	
	private Long id;
	
	@NotNull
	private String addressLine1;
	private String addressLine2;
	@NotNull
	private String city;
	@NotNull
	private String state;
	@NotNull
	private String zipcode;
	@NotNull
	private String country;
	@NotNull
	private String username;
	
	
	public AddressVm() {
	}
	
	public AddressVm(Long id, String addressLine1, String addressLine2, String city, String state, String zipcode,
			String country, String username) {
		super();
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
		this.username = username;
		this.id = id;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "AddressVm [id=" + id + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city="
				+ city + ", state=" + state + ", zipcode=" + zipcode + ", country=" + country + ", username=" + username
				+ "]";
	}
	
}
