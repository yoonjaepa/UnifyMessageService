package com.jds.ums.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.jds.ums.common.JsonUtil;
import com.jds.ums.component.KafkaMessageSender;
import com.jds.ums.repository.RequestRepo;
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
	
	private String requestJson;
	private RequestMasterVO requestVo;
	
	private void setRequest(String request) {
		
		if (requestValidator(request)) {
			this.requestJson = request;
		}
		//	client 요청 json 을 객체로 변경 
		this.requestVo = (RequestMasterVO) JsonUtil.Json2Object(request, RequestMasterVO.class);
		//	전문 저장 
		this.requestVo.setRequestDoc(this.requestJson);
		
		//상세 요청에 대한 마스터의 UUID 정보 세팅 
		this.requestVo.setRequestDtlMasterUid(this.requestVo.getRequestMasterUid());
		
	}
	
	private String registRequest() {
		
		
		//	insert request Master
		requestRepo.registRequestMaster(requestVo);
		
		//	insert request detail info
		requestRepo.registRequestDetail(requestVo.getRequestDetail());
		
		
		return null;
	}
	
	private boolean requestValidator(String request) {
		
		return true;
	}

	@Override
	public void transferRequest(String request) {
		
		this.setRequest(request);
		this.registRequest();
		
		//	client 에서 요청한 내용을 MQ로 전달
		kafkaMessageSender.send(this.requestVo);

	}
	

	

}
