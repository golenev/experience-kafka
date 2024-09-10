package com.experience_kafka.controller;

import com.experience_kafka.model.Messages;
import com.experience_kafka.model.MyUser;
import com.experience_kafka.repository.RecordRepository;
import com.experience_kafka.service.KafkaService;
import com.experience_kafka.service.MyUserDetailsService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private RecordRepository recordRepository;



    @PostMapping("/send")
    public void sendMessage(@RequestBody List<String> messages) {
        kafkaService.sendMessage("send-topic", messages);
    }

    @GetMapping("/records")
    public List<Messages> getAllRecords() {
        return recordRepository.findAll();
    }

    @GetMapping("/test")
    public String testController() {
        return "Controller is working";
    }

    @GetMapping("/fetch-records")
    public List<Messages> fetchAndSaveRecords() {
        List<String> messages = kafkaService.fetchMessagesFromBeginning("send-topic");
        List<Messages> existingMessages = recordRepository.findAll();

        // Фильтруем сообщения, которые уже есть в базе данных
        List<String> newMessages = messages.stream()
                .filter(message -> existingMessages.stream()
                        .noneMatch(existingMessage -> existingMessage.getMessage().equals(message)))
                .toList();
        // Сохраняем новые сообщения в базу данных
        recordRepository.saveAll(newMessages.stream().map(Messages::new).toList());
        // Возвращаем все сообщения из базы данных
        return recordRepository.findAll();
    }



}
