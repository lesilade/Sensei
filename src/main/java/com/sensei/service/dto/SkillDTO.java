package com.sensei.service.dto;

import com.sensei.domain.Skill;
import com.sensei.domain.User;

public class SkillDTO {
	
	private String name;
	private String username;
	
	
	public SkillDTO() {
	}


	public SkillDTO(String name, String username) {
		super();
		this.name = name;
		this.username = username;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	public Skill convert(final SkillDTO skillDTO, User user)
	{
		Skill skill = new Skill();
		skill.setName(skillDTO.getName());
		skill.setUser(user);
		return skill;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		SkillDTO other = (SkillDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SkillDTO [name=" + name + ", username=" + username + "]";
	}
	

}
