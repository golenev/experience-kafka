package com.experience_kafka;

import com.experience_kafka.config.KafkaConfig;
import com.experience_kafka.model.Message;
import com.experience_kafka.repository.MessageRepository;
import com.experience_kafka.service.KafkaConsumerService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;


@SpringBootTest
@EmbeddedKafka(topics = "test-topic", partitions = 1)
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.auto-offset-reset=earliest"
})
public class KafkaToDatabaseTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testSendMessageToKafkaAndCheckDatabase() {
        Message message = new Message();
        message.setContent("Hello, Qwerty!");

        // Отправка сообщения в Kafka
        kafkaTemplate.send("test-topic", message);

    }
}