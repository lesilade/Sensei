package com.sensei.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sensei.domain.Messaging;
import com.sensei.repository.MessagingRepository;

@Service
@Transactional
public class MessageService 
{
	
	 private MessagingRepository repo;
	 
	 public MessageService(final MessagingRepository repo) 
	 {
		 this.repo = repo;
	 }
	    
	 public List<Messaging> findAll() 
	 {
	      return repo.findAll();
	 }
	    
	 public void save(Messaging message) 
	 {
	     repo.save(message);
	 }
}
