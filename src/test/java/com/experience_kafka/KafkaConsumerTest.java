package com.experience_kafka;

import com.experience_kafka.model.MessageEntity;
import com.experience_kafka.repository.MessageRepository;
import com.experience_kafka.service.KafkaProducerService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class KafkaConsumerTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private Consumer<String, MessageEntity> consumer;

    @BeforeEach
    public void setup() {
        // Настройка KafkaConsumer для получения сообщений
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        //consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(List.of("test-topic"));
    }

    @AfterEach
    public void tearDown() {
        // Закрытие KafkaConsumer после каждого теста
        if (consumer != null) {
            consumer.close();
        }
    }

    @Test
    public void testReceiveMessageFromKafkaAndSaveToDatabase() throws InterruptedException {
        // Создание тестового сообщения
        String message = "Hello, Kafka!";
        var mess = new MessageEntity();
        mess.setContent(message);
        // Отправка сообщения в Kafka
        kafkaProducerService.sendMessage("test-topic", mess);
        // Ожидание, чтобы дать время Kafka обработать сообщение
        ConsumerRecords<String, MessageEntity> records = consumer.poll(Duration.ofMillis(10000L));
        int y = 0;
   }
}
