package com.experience_kafka.controller;

import com.experience_kafka.model.Messages;
import com.experience_kafka.repository.RecordRepository;
import com.experience_kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class PostgresController {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private RecordRepository recordRepository;

    @GetMapping("/records")
    public List<Messages> getAllRecords() {
        return recordRepository.findAll();
    }

    @DeleteMapping("/deleteRecords")
    public void deleteAllRecords() {
         recordRepository.deleteAll();
    }

    //Если сообщений в кафке накопилось достаточно, этой ручкой можно отправить их базу,
    //а из Кафки они сразу удаляются
    @GetMapping("/saveToRepository")
    public Map<String, Object> saveToRepositoryIfConditions() {
        List<String> messages = kafkaService.fetchMessagesFromBeginning("send-topic");
        Map<String, Object> response = new HashMap<>();
        if (messages.size() >= 10) {
            recordRepository.saveAll(messages.stream().map(Messages::new).toList());
            response.put("status", !messages.isEmpty());
            response.put("message", !messages.isEmpty() ? "Сообщения найдены и отправлены в базу" : "Сообщений нет");
            kafkaService.deleteAndRecreateTopic("send-topic");
            return response;
        } else
            response.put("message", "Сообщений недостаточно, пока только " + messages.size() + ", нужно ещё " + (10 - messages.size()));
        return response;
    }

}
