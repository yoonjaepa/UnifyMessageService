package com.jds.ums.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import com.github.javaparser.utils.Log;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaTopicConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${kafka.my.push.topic.name}")
	private String topicName;
	
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		
		return new KafkaAdmin(configs);
	}
	
	@Bean
	public NewTopic newTopic() {
		
		return new NewTopic(topicName, 3, (short) 3);
	}
	
}
