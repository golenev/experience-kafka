package isolatedKafkaTests;

import isolatedKafka.SendService;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.support.serializer.JsonSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SendServiceTest {


    @Test
    void sendRecords() {
        String bootstrapServers = "localhost:9092";
        String topicName = "send-topic";
        SendService service = new SendService(bootstrapServers, topicName);
        service.sendRecords(List.of("test1", "test2", "test2"));

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-java-test");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Consumer consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicName));
        ConsumerRecords records = consumer.poll(Duration.ofMillis(10000L));
        // Вывод содержимого records в консоль
        System.out.println("вывод в консоль");
        records.records(topicName).forEach(System.out::println);
        consumer.close();
    }
}
