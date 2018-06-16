package com.sensei.web.rest.vm;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EducationVM {
	
    @NotNull
    private String school;

    @NotNull
    private String degree;

    //@NotNull
    private String majorareaofstudy;

    //@Size(max = 250)
    private String minorareaofstudy;

    //@NotNull
    private String startdate;

    //@NotNull
    private String enddate;

    //@NotNull
    private String content;

    private Boolean iscurrent;

    //@NotNull
    private String username;

    
    
	public EducationVM() {
	}

	public EducationVM(String school, String degree, String majorareaofstudy, String minorareaofstudy,
			String startdate, String enddate, String content, Boolean iscurrent, String username) {
		super();
		this.school = school;
		this.degree = degree;
		this.majorareaofstudy = majorareaofstudy;
		this.minorareaofstudy = minorareaofstudy;
		this.startdate = startdate;
		this.enddate = enddate;
		this.content = content;
		this.iscurrent = iscurrent;
		this.username = username;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getMajorareaofstudy() {
		return majorareaofstudy;
	}

	public void setMajorareaofstudy(String majorareaofstudy) {
		this.majorareaofstudy = majorareaofstudy;
	}

	public String getMinorareaofstudy() {
		return minorareaofstudy;
	}

	public void setMinorareaofstudy(String minorareaofstudy) {
		this.minorareaofstudy = minorareaofstudy;
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
		return "EducationVM [school=" + school + ", degree=" + degree + ", majorareaofstudy=" + majorareaofstudy
				+ ", minorareaofstudy=" + minorareaofstudy + ", startdate=" + startdate + ", enddate=" + enddate
				+ ", content=" + content + ", iscurrent=" + iscurrent + ", username=" + username + "]";
	}
    

}
