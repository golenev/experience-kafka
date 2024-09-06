package com.experience_kafka.service;

import com.experience_kafka.model.MessageEntity;
import com.experience_kafka.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "send-topic", groupId = "group-java-test")
    public void listen(String message) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setContent(message);
        messageRepository.save(messageEntity);
    }
}
