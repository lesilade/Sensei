package com.sensei.web.rest.vm;

public class LikeVmResponse {
	
    private Long newsfeedId;
	private Long count;
	
	
	public LikeVmResponse() {
	}

	public LikeVmResponse(Long newsfeedId, Long count) {
		super();
		this.newsfeedId = newsfeedId;
		this.count = count;
	}

	public Long getNewsfeedId() {
		return newsfeedId;
	}

	public void setNewsfeedId(Long newsfeedId) {
		this.newsfeedId = newsfeedId;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "LikeVmResponse [newsfeedId=" + newsfeedId + ", count=" + count + "]";
	}
	
}
