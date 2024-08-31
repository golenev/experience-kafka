package com.experience_kafka.service;

import com.experience_kafka.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.experience_kafka.repository.MessageRepository;

@Service
public class KafkaConsumerService {

    @Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(Message message) {
        messageRepository.save(message);
    }
}
