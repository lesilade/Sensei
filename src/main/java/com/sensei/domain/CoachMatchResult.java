package com.sensei.domain;

public class CoachMatchResult {

	private String id;
	private String login;
	private String firs_name;
	private String last_name;
	private String email;
	private String city;
	private String state;
	private String zipcode;
	
	
	public CoachMatchResult() {

	}

	public CoachMatchResult(String id, String login, String firs_name, String last_name, String email, String city,
			String state, String zipcode) {
		super();
		this.id = id;
		this.login = login;
		this.firs_name = firs_name;
		this.last_name = last_name;
		this.email = email;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getFirs_name() {
		return firs_name;
	}


	public void setFirs_name(String firs_name) {
		this.firs_name = firs_name;
	}


	public String getLast_name() {
		return last_name;
	}


	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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


	@Override
	public String toString() {
		return "CoachMatchResult [id=" + id + ", login=" + login + ", firs_name=" + firs_name + ", last_name="
				+ last_name + ", email=" + email + ", city=" + city + ", state=" + state + ", zipcode=" + zipcode + "]";
	}


	
	
}
