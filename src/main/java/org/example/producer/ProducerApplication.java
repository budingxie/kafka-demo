package org.example.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.config.KafkaConfigConstant;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * description
 *
 * @author budingxie
 * @version 1.0.0
 * @date 2021/2/25
 */
public class ProducerApplication {


    public static void main(String[] args) {

        System.out.println("start=======开始发送消息");

        // 1.指定配置信息
        Properties prop = new Properties();
        // bootstrap.servers，broker地址，可以指定多个：127.0.0.1:9092,localhost:9092
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfigConstant.BROKER_IP);
        // key和value 序列化类
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // 2.创建生产者
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(prop);

        // 3.发送消息
        String message = "hello world";
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(KafkaConfigConstant.TOPIC, message);
        // 发送消息，返回，消息存储的元信息；可以在send()方法中设置回调，判断是否成功发送: Callback callback
        Future<RecordMetadata> future = kafkaProducer.send(record);
        System.out.println("end=======消息发送成功");

        // 4.关闭生产者
        kafkaProducer.close();
    }


}
