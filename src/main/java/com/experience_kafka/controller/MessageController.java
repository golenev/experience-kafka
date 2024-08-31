package com.experience_kafka.controller;


import com.experience_kafka.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.experience_kafka.service.KafkaProducerService;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping
    public void sendMessage(@RequestBody Message message) {
        kafkaProducerService.sendMessage(message);
    }

    @GetMapping("/test")
    public String test() {
        return "Controller is working";
    }

}
