package com.sensei.web.rest.vm;

import java.util.ArrayList;
import java.util.List;

import com.sensei.service.dto.SkillDTO;

public class SkillVM {
	
	List<SkillDTO> skills = new ArrayList<SkillDTO>();
	
	
	public SkillVM() 
	{

	}

	public SkillVM(List<SkillDTO> skills)
	{
		super();
		this.skills = skills;
	}

	public List<SkillDTO> getSkills() 
	{
		return skills;
	}

	public void setSkills(List<SkillDTO> skills) 
	{
		this.skills = skills;
	}
	
}
