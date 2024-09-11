package com.experience_kafka.controller;

import com.experience_kafka.model.Messages;
import com.experience_kafka.repository.RecordRepository;
import com.experience_kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private RecordRepository recordRepository;

    @PostMapping("/send")
    public String sendMessage(@RequestBody List<String> messages) {
        kafkaService.sendMessage("send-topic", messages);
        return "Сообщение отправлено в Кафку";
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
    public Map<String, Object> fetchAndSaveRecords() {
        List<String> messages = kafkaService.fetchMessagesFromBeginning("send-topic");
        List<Messages> existingMessages = recordRepository.findAll();

        // Фильтруем сообщения, которые уже есть в базе данных
        List<String> newMessages = messages.stream()
                .filter(message -> existingMessages.stream()
                        .noneMatch(existingMessage -> existingMessage.getMessage().equals(message)))
                .toList();
        // Сохраняем новые сообщения в базу данных
        recordRepository.saveAll(newMessages.stream().map(Messages::new).toList());

        // Возвращаем JSON-объект с сообщением и статусом
        Map<String, Object> response = new HashMap<>();
        response.put("status", !messages.isEmpty());
        response.put("message", !messages.isEmpty() ? "Сообщения найдены и отправлены в базу" : "Сообщений нет");
        return response;
    }

    @GetMapping("/recreateTopic")
    public String recreateTopic () {
        kafkaService.deleteAndRecreateTopic("send-topic");
        return "Топик успешно удалён и создан заново";
    }

}
