package com.jds.ums.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.kafka.support.SendResult;

import com.jds.ums.vo.RequestMasterVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaMessageSender {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KafkaTemplate<String, RequestMasterVO> kafkaTemplate;
	
	@Value("${kafka.my.push.topic.name}")
	private String topicName;
	
	public void send(RequestMasterVO request) {
		
		Message<RequestMasterVO> message = MessageBuilder
				.withPayload(request)
				.setHeader(KafkaHeaders.TOPIC, topicName)
				.build();
		log.info("카프카로 메시지 보내기['{}'] !!", message.toString());
		

		ListenableFuture<SendResult<String, RequestMasterVO>> future = kafkaTemplate.send(message);
		
//		logger.info("카프카로 메시지 보내기 2 !!");
		
		future.addCallback(new ListenableFutureCallback<SendResult<String, RequestMasterVO>>() {
			
						
			@Override
			public void onSuccess(SendResult<String, RequestMasterVO> stringObjectSendResult) {
				
				
				
				log.info("Send Message=['{}'] with offset=['{}']", stringObjectSendResult.getProducerRecord().value().toString()
						, stringObjectSendResult.getRecordMetadata().offset());
				
			}

			@Override
			public void onFailure(Throwable ex) {
				log.info("Unable to send message=[] due to :" + ex.getMessage());
				
			}			
			
		});
	}
	
	
}
