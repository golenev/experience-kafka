package com.experience_kafka;

import com.experience_kafka.controller.MessageController;
import com.experience_kafka.model.Message;
import com.experience_kafka.repository.MessageRepository;
import com.experience_kafka.service.KafkaProducerService;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

public class KafkaConsumerTest {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    private MessageRepository messageRepository;

    private Consumer<String, Message> consumer;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private MessageController messageController;

    @Test
    @PostConstruct

    public void testReceiveMessageFromKafkaAndSaveToDatabase() throws InterruptedException {
        Message message = new Message();
        message.setContent("Hello, Kafka123123123!");

        kafkaProducerService.sendMessage(message);

        var list = messageController.getAllMessages();
        int y = 0;
    }

}

//        // Получение сообщения из Kafka
//        ConsumerRecord<String, Message> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "test-topic");
//        Message receivedMessage = singleRecord.value();
//
//        // Проверка, что сообщение было получено
//        assertEquals("Hello, Kafka!", receivedMessage.getContent());
//
//        // Проверка, что сообщение было сохранено в базе данных
//        Message savedMessage = messageRepository.findById(receivedMessage.getId()).orElse(null);
//        assertNotNull(savedMessage);
//        assertEquals("Hello, Kafka!", savedMessage.getContent());
//    }
//}