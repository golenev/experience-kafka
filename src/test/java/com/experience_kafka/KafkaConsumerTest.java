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
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext
public class KafkaConsumerTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;


    @Test
    @PostConstruct

    public void testReceiveMessageFromKafkaAndSaveToDatabase() throws InterruptedException {
        // Создание тестового сообщения
        Message message = new Message();
        message.setContent("Hello, Kafka!");

        // Отправка сообщения в Kafka
        kafkaProducerService.sendMessage(message);

        // Ожидание, пока сообщение будет обработано и сохранено в базе данных
        await().atMost(20, TimeUnit.SECONDS).untilAsserted(() -> {
            // Проверка, что сообщение сохранено в базе данных
            assertThat(messageRepository.findAll()).anyMatch(msg -> msg.getContent().equals("Hello, Kafka!"));
        });

    }

}

