package com.jds.ums.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.jds.ums.vo.RequestMasterVO;



@Configuration
public class KafkaProducerConfig {

	@Value("${kafka.bootstrapAddress}")
	private String bootstrapServers;
	
	@Bean
	public ProducerFactory<String, RequestMasterVO> producerFactory() {
		Map<String, Object> configProps = producerFactoryConfig();
		
		return new DefaultKafkaProducerFactory<>(configProps);			
		
	}
	

	private Map<String, Object> producerFactoryConfig(){
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		
		return configProps;
	}
	
	@Bean
	public KafkaTemplate<String, RequestMasterVO> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}
	

}
