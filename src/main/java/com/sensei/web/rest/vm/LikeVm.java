package com.sensei.web.rest.vm;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LikeVm {

	private Long count;

	
	public LikeVm() {
	}
	
	
	public LikeVm(Long count) {
		super();
		this.count = count;
	}



	public Long getCount() {
		return count;
	}


	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "LikeVm [count=" + count + "]";
	}
	
	
}
