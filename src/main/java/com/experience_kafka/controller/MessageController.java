package com.experience_kafka.controller;


import com.experience_kafka.model.Message;
import com.experience_kafka.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.experience_kafka.service.KafkaProducerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping
    public void sendMessage(@RequestBody Message message) {
        kafkaProducerService.sendMessage(message);
    }

    @GetMapping("/test")
    public String test() {
        return "Controller is working";
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

}
