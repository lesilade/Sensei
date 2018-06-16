package com.sensei.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sensei.domain.Messaging;
import com.sensei.service.MessageService;



@RestController
@RequestMapping("/messages")
@CrossOrigin
public class MessageController {
    
    private static final String MESSAGES_CHANNEL = "/topic/messages";
    
    private MessageService messageService;
    
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }
    
    @GetMapping
    public List<Messaging> findAll() {
        return messageService.findAll();
    }
    
    @PostMapping
    public void save(@RequestBody Messaging message) {
        messageService.save(message);
        messagingTemplate.convertAndSend(MESSAGES_CHANNEL, message);
    }

}
