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

    @PostMapping("/sendToKafka")
    public String sendMessage(@RequestBody List<String> messages) {
        kafkaService.sendMessage("send-topic", messages);
        return "Сообщение отправлено в Кафку";
    }

    @GetMapping("/showMessages")
    public List<String> showMessages() {
        return kafkaService.fetchMessagesFromBeginning("send-topic");
    }

    @GetMapping("/recreateTopic")
    public String recreateTopic() {
        kafkaService.deleteAndRecreateTopic("send-topic");
        return "Топик успешно удалён и создан заново";
    }

}
