package com.experience_kafka.service;


import com.experience_kafka.model.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, MessageEntity> kafkaTemplate;

    public void sendMessage(String topic, MessageEntity message) {
        kafkaTemplate.send(topic, message);
    }
}