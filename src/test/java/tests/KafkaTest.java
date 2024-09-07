package tests;

import com.experience_kafka.KafkaSpringApplication;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;
import java.util.*;

@SpringBootTest
@ContextConfiguration(classes = KafkaSpringApplication.class)
public class KafkaTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    @Test
    void test() {
        String topicName = "send-topic";

        Consumer<String, String> consumer = consumerFactory.createConsumer();

        // Указываем, что хотим читать с самого начала
        TopicPartition partition = new TopicPartition("send-topic", 0);
        consumer.assign(Collections.singletonList(partition));
        consumer.seekToBeginning(Collections.singletonList(partition));


        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));


        consumer.close();
    }

}
