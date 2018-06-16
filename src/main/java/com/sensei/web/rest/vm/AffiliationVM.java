package com.sensei.web.rest.vm;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sensei.domain.User;

public class AffiliationVM {
	
    @NotNull
    private String organization;

    @NotNull
    private String role;

    @NotNull
    private String focusarea;

    @NotNull
    private String startdate;

    @NotNull
    private String enddate;

    
    private Boolean iscurrent;
    
    @NotNull
    private String username;
    

	public AffiliationVM() {
	}

	public AffiliationVM(String organization, String role, String focusarea, String startdate, String enddate,
			Boolean iscurrent, String username) {
		this.organization = organization;
		this.role = role;
		this.focusarea = focusarea;
		this.startdate = startdate;
		this.enddate = enddate;
		this.iscurrent = iscurrent;
		this.username = username;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFocusarea() {
		return focusarea;
	}

	public void setFocusarea(String focusarea) {
		this.focusarea = focusarea;
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
		return "AffiliationVM [organization=" + organization + ", role=" + role + ", focusarea=" + focusarea
				+ ", startdate=" + startdate + ", enddate=" + enddate + ", iscurrent=" + iscurrent + ", username="
				+ username + "]";
	}
    
    

}
