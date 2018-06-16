package com.sensei.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UserProfile {

	private User user;
	private List<UserSkill> userSkills;
	private List<UserExperience> userExperience;
	private List<UserAffiliation> userAffiliation;
	private List<UserEducation> userEducation;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm");

	public UserProfile() {
	}
	
	public UserProfile(User user, List<UserSkill> userSkills, List<UserExperience> userExperience,
			List<UserEducation> userEducation, List<UserAffiliation> userAffiliation) {
		super();
		this.user = user;
		this.userSkills = userSkills;
		this.userExperience = userExperience;
		this.userEducation = userEducation;
		this.userAffiliation = userAffiliation;
	}
	
	public List<UserSkill> getUserSkills() {
		return userSkills;
	}

	public void setUserSkills(List<Skill> skills) {
		
		this.userSkills = skills.stream().map(skill -> 
		new UserSkill(skill.getId(), skill.getName()))
				.collect(Collectors.toList());
	}

	public List<UserExperience> getUserExperience() {
		return userExperience;
	}

	public void setUserExperience(List<Experience> experiences) {

		this.userExperience = experiences.stream().map(experience ->
		{
			String endDate = null;
			if(experience.getEnddate() != null)
			{
				endDate = experience.getEnddate().format(dateFormatter);
			}

			return new UserExperience(experience.getId(), experience.getTitle(), experience.getOrganization(),
					experience.getCity(), experience.getState(), experience.getStartdate().format(dateFormatter),
					endDate, experience.getContent(), experience.isIscurrent());
		}).collect(Collectors.toList());
	}

	public List<UserEducation> getUserEducation() {
		return userEducation;
	}

	public void setUserEducation(List<Education> educations) {
		this.userEducation = educations.stream().map(education -> 
				new UserEducation(education.getId(), education.getSchool(), education.getDegree(), education.getMajorareaofstudy(),
						education.getMinorareaofstudy(), education.getStartdate(),education.getEnddate(),
						 education.isIscurrent()))
						.collect(Collectors.toList());
	}

	public List<UserAffiliation> getUserAffiliation() {
		return userAffiliation;
	}

	public void setUserAffiliation(List<Affiliation> affiliations) {
		
		this.userAffiliation = affiliations.stream().map(affiliation -> 
		new UserAffiliation(affiliation.getId(), affiliation.getOrganization(), affiliation.getRole(),
				affiliation.getFocusarea(), affiliation.getStartdate(),affiliation.getEnddate(), affiliation.isIscurrent()))
				.collect(Collectors.toList());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userEducation == null) ? 0 : userEducation.hashCode());
		result = prime * result + ((userAffiliation == null) ? 0 : userAffiliation.hashCode());
		result = prime * result + ((userExperience == null) ? 0 : userExperience.hashCode());
		result = prime * result + ((userSkills == null) ? 0 : userSkills.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile other = (UserProfile) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userEducation == null) {
			if (other.userEducation != null)
				return false;
		} else if (!userEducation.equals(other.userEducation))
			return false;
		if (userAffiliation == null) {
			if (other.userAffiliation != null)
				return false;
		} else if (!userAffiliation.equals(other.userAffiliation))
			return false;
		if (userExperience == null) {
			if (other.userExperience != null)
				return false;
		} else if (!userExperience.equals(other.userExperience))
			return false;
		if (userSkills == null) {
			if (other.userSkills != null)
				return false;
		} else if (!userSkills.equals(other.userSkills))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "UserProfile [userSkills=" + userSkills + ", userExperience=" + userExperience + ", userEducation="
				+ userEducation + ", userAffiliation=" + userAffiliation + ", user=" + user + "]";
	}

	class UserSkill 
	{
		private long id;
		private String name;
		
		
		public UserSkill(long id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	class UserAffiliation
	{
		private long id;
		private String organization;
		private String role;
		private String focusArea;
		private LocalDateTime startdate;
		private LocalDateTime enddate;
		private Boolean isCurrent;
		
		public UserAffiliation(long id, String organization, String role, String focusArea, LocalDateTime startdate,
				LocalDateTime enddate, Boolean isCurrent) {
			super();
			this.id = id;
			this.organization = organization;
			this.role = role;
			this.focusArea = focusArea;
			this.startdate = startdate;
			this.enddate = enddate;
			this.isCurrent = isCurrent;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
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

		public String getFocusArea() {
			return focusArea;
		}

		public void setFocusArea(String focusArea) {
			this.focusArea = focusArea;
		}

		public LocalDateTime getStartdate() {
			return startdate;
		}

		public void setStartdate(LocalDateTime startdate) {
			this.startdate = startdate;
		}

		public LocalDateTime getEnddate() {
			return enddate;
		}

		public void setEnddate(LocalDateTime enddate) {
			this.enddate = enddate;
		}

		public Boolean getIsCurrent() {
			return isCurrent;
		}

		public void setIsCurrent(Boolean isCurrent) {
			this.isCurrent = isCurrent;
		}
		
	}
	
	class UserEducation 
	{
	
		 private long id;
		 private String school;
		 private String degree;
		 private String majorareaofstudy;
		 private String minorareaofstudy;
		 private LocalDateTime startdate;
		 private LocalDateTime enddate;
		 private String content;
		 private boolean isCurrent;
		 
		public UserEducation(long id, String school, String degree, String majorareaofstudy, String minorareaofstudy,
				LocalDateTime startdate, LocalDateTime enddate, boolean isCurrent) {
			super();
			this.id = id;
			this.school = school;
			this.degree = degree;
			this.majorareaofstudy = majorareaofstudy;
			this.minorareaofstudy = minorareaofstudy;
			this.startdate = startdate;
			this.enddate = enddate;
			this.content = content;
			this.isCurrent = isCurrent;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
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

		public LocalDateTime getStartdate() {
			return startdate;
		}

		public void setStartdate(LocalDateTime startdate) {
			this.startdate = startdate;
		}

		public LocalDateTime getEnddate() {
			return enddate;
		}

		public void setEnddate(LocalDateTime enddate) {
			this.enddate = enddate;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public boolean isCurrent() {
			return isCurrent;
		}

		public void setCurrent(boolean isCurrent) {
			this.isCurrent = isCurrent;
		}
		
	}
	
	
	class UserExperience 
	{
		private long id;
		private String title;
		private String organization;
		private String city;
		private String state;
		private String startdate;
		private String enddate;
		private String content ;
		private Boolean isCurrent;
		
		
		public UserExperience(long id, String title, String organization, String city, String state,
				String startdate, String enddate, String content, Boolean isCurrent) {
			super();
			this.id = id;
			this.title = title;
			this.organization = organization;
			this.city = city;
			this.state = state;
			this.startdate = startdate;
			this.enddate = enddate;
			this.content = content;
			this.isCurrent = isCurrent;
		}


		public long getId() {
			return id;
		}


		public void setId(long id) {
			this.id = id;
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


		public Boolean getIsCurrent() {
			return isCurrent;
		}


		public void setIsCurrent(Boolean isCurrent) {
			this.isCurrent = isCurrent;
		}
		
	}
	
}
