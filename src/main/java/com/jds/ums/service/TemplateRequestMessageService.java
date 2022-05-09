package com.jds.ums.service;

import java.util.Map;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.jds.ums.common.JsonUtil;
import com.jds.ums.component.KafkaMessageSender;
import com.jds.ums.repository.RequestRepo;
import com.jds.ums.vo.RequestDetailVO;
import com.jds.ums.vo.RequestMasterVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TemplateRequestMessageService implements IFRequestMessageService {

	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KafkaMessageSender kafkaMessageSender;
	
	@Autowired
	private RequestRepo requestRepo;
	
	@Value("${kafka.my.push.topic.name}")
	private String topicName;
	
	
	@Override
	public boolean validateRequest(RequestMasterVO request) {

		
		RequestDetailVO[] detail = request.getRequestDetail();

		Map<String, String> params = detail[1].getTemplateVariable();
		
		Set<String> keys = params.keySet();
		
		for(String key: keys) {
			log.info(key +" : " +params.get(key));
		}
		
		return true;
	}

	@Override
	public String registRequest(RequestMasterVO request) {
		
		//	Object --> Json String
		request.setRequestDoc(JsonUtil.object2Json(request));
		
		//	insert request master info
		requestRepo.registRequestMaster(request);
		
		//	insert request detail info
		requestRepo.registRequestDetail(request.getRequestDetail(), request.getRequestMasterUid());
		
		
		return null;
	}

	@Override
	public void transferRequest(RequestMasterVO request) {
		
		kafkaMessageSender.send(request);

	}

}
