package org.example.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.config.KafkaConfigConstant;

import java.util.Collections;
import java.util.Properties;

/**
 * description
 *
 * @author budingxie
 * @version 1.0.0
 * @date 2021/2/25
 */
public class ConsumerApplication {

    public static void main(String[] args) {
        // 1.消费者配置
        Properties prop = new Properties();
        // broker 地址
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfigConstant.BROKER_IP);
        // 反序列化
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 消费组配置，不写采用默认的
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConfigConstant.CONSUMER_GROUP);

        // 2.创建消费者
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(prop);

        // 3.订阅topic
        kafkaConsumer.subscribe(Collections.singleton(KafkaConfigConstant.TOPIC));

        // 4.poll消息
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("topic:%s,offset:%d,消息:%s",
                        record.topic(), record.offset(), record.value()));
            }
        }
    }

}
