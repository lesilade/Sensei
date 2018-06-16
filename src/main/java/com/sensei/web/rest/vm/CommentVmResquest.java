package com.sensei.web.rest.vm;

public class CommentVmResquest {
	
	  private String content;
      private Long newsfeedId;
      
	  
      
    public CommentVmResquest()
    {
    }

	public CommentVmResquest(String content, Long newsfeedId) 
	{
		super();
		this.content = content;
		this.newsfeedId = newsfeedId;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content) 
	{
		this.content = content;
	}

	public Long getNewsfeedId() 
	{
		return newsfeedId;
	}

	public void setNewsfeedId(Long newsfeedId) 
	{
		this.newsfeedId = newsfeedId;
	}

	@Override
	public String toString() 
	{
		return "CommentVmResquest [content=" + content + ", newsfeedId=" + newsfeedId + "]";
	}
	

}
