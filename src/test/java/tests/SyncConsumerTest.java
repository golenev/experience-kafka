package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SyncConsumerTest {

    public class GetService {
        private String topicName;
        private Properties properties;

        public GetService(String bootstrapServers, String topicName) {
            this.topicName = topicName;
            properties = new Properties();
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-java");
            properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        }

        public ConsumerRecords getRecords() {
            Consumer consumer = new KafkaConsumer(properties);
            consumer.subscribe(Arrays.asList(topicName));
            ConsumerRecords records = consumer.poll(Duration.ofMillis(5000));
            consumer.close();
            return records;
        }
    }

    public String getAuthCookie() {
        Response response = RestAssured.given()
                .formParam("username", "admin")
                .formParam("password", "qwerty")
                .redirects().follow(true)
                .post("http://localhost:6789/login");
        response.then().log().all();
        String sessionCookie = response.getCookie("JSESSIONID");
        return sessionCookie;
    }

    public void sendToKafkaViaRestAssured(String authCookie) {
        RestAssured.baseURI = "http://localhost:6789";
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + authCookie)
                .contentType(ContentType.JSON)
                .body(List.of("sent", "test", "message"))
                .log().all()
                .post("/api/v1/sendToKafka")
                .then().log().all();
    }

    @Test
    void consumerSyncTestt() {
        String cookie = getAuthCookie();
        sendToKafkaViaRestAssured(cookie);
        GetService getService = new GetService("localhost:9092", "send-topic");
        getService.getRecords();
    }

}
