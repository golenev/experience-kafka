package com.experience_kafka.controller;


import com.experience_kafka.model.MessageEntity;
import com.experience_kafka.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.experience_kafka.service.KafkaProducerService;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody MessageEntity message) {
        kafkaProducerService.sendMessage("test-topic", message);
    }

    @GetMapping("/test")
    public String test() {
        return "Controller is working";
    }

    @GetMapping
    public List<MessageEntity> getAllMessages() {
        return messageRepository.findAll();
    }

}
