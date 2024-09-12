package com.experience_kafka.service;

import com.experience_kafka.model.Messages;
import com.experience_kafka.repository.RecordRepository;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    @Autowired
    private KafkaAdmin kafkaAdmin;

    public void sendMessage(String topic, List<String> messages) {
        messages.forEach(message -> kafkaTemplate.send(topic, message));
    }

    //этот метод автоматически отправляет сообщения из кафки в базу
//    @KafkaListener(topics = "send-topic", groupId = "group-java-test")
//    public void listen(String message) {
//        Messages record = new Messages();
//        record.setMessage(message);
//        recordRepository.save(record);
//    }

    public List<String> fetchMessagesFromBeginning(String topic) {
        Consumer<String, String> consumer = consumerFactory.createConsumer();
        List<String> messages = new ArrayList<>();
        // Указываем, что хотим читать с самого начала
        TopicPartition partition = new TopicPartition(topic, 0);
        consumer.assign(Collections.singletonList(partition));
        consumer.seekToBeginning(Collections.singletonList(partition));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    messages.add(record.value());
                }
                if (records.isEmpty()) {
                    // Если сообщений больше нет, выходим из цикла
                    break;
                }
            }
        } finally {
            consumer.close();
        }
        return messages;
    }

    public void deleteAndRecreateTopic(String topic) {
        Map<String, Object> configs = kafkaAdmin.getConfigurationProperties();
        try (AdminClient adminClient = AdminClient.create(configs)) {
            // Удаляем топик
            DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Collections.singletonList(topic));
            deleteTopicsResult.all().get();
            // Проверяем, удален ли топик
                Thread.sleep(5000); // Проверяем каждую секунду
            // Создаем топик заново
            NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
            CreateTopicsResult createTopicsResult = adminClient.createTopics(Collections.singletonList(newTopic));
            createTopicsResult.all().get();
            System.out.println("Топик " + topic + " удален и создан заново.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
