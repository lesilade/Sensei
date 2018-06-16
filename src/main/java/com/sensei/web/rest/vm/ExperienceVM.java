package com.sensei.web.rest.vm;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class ExperienceVM {
	
    @NotNull
    private String title;
    
    @NotNull
    private String organization;

    @NotNull
    private String city;

    @NotNull
    private String state;

	@NotNull
	private String startdate;

	private String enddate;

    @NotNull
    private String content;

    @NotNull
    private Boolean iscurrent;

    @NotNull
    private String username;


	public ExperienceVM() {
	}

	public ExperienceVM(String title, String organization, String city, String state, String startdate,
			String enddate, String content, Boolean iscurrent, String username) {
		super();
		this.title = title;
		this.organization = organization;
		this.city = city;
		this.state = state;
		this.startdate = startdate;
		this.enddate = enddate;
		this.content = content;
		this.iscurrent = iscurrent;
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
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

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIscurrent() {
		return iscurrent;
	}

	public void setIscurrent(Boolean iscurrent) {
		this.iscurrent = iscurrent;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "ExperienceVM [title=" + title + ", organization=" + organization + ", city=" + city + ", state=" + state
				+ ", startdate=" + startdate + ", enddate=" + enddate + ", content=" + content + ", iscurrent="
				+ iscurrent + ", username=" + username + "]";
	}
    

}
