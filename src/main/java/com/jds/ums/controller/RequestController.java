package com.jds.ums.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jds.ums.service.IFRequestMessageService;
import com.jds.ums.vo.RequestMasterVO;

import lombok.extern.slf4j.Slf4j;




@RestController
@Slf4j
public class RequestController {
	
	//private Logger logger =  LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private IFRequestMessageService templateRequestMessageService;
	
	
	
	@GetMapping("/mass")
	public String massRequestSendMessaage() {
		
		log.info("hello Mass");
		
		return "Project Name is UMS!!";
	}
	
	@PostMapping("/requestTemplate")
	public String templateRequestSendMessaage(@RequestBody  RequestMasterVO request) {
		
		
		log.info(request.toString());
		

		
		//kafkaMessageSender.send(request);
		
		
		if (templateRequestMessageService.validateRequest(request)) {
			
			//templateRequestMessageService.transferRequest(request);
			templateRequestMessageService.registRequest(request);
			
			return request.toString();
		} else return "Error";
		
//		return "Test";
		
	}
	
	@PostMapping("/request")
	public String templateRequest(@RequestBody  String request) {
		
		
		log.info(request);
		
		//logger.info(jSonStr);
		
		//kafkaMessageSender.send(request);
		
		
		/*
		 * if (templateRequestMessageService.validateRequest(request)) {
		 * 
		 * //templateRequestMessageService.transferRequest(request);
		 * templateRequestMessageService.registRequest(request);
		 * 
		 * return request.toString(); } else return "Error";
		 */
		
		return null;
		
//		return "Test";
		
	}
	

}
