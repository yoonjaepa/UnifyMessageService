package com.jds.ums.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jds.ums.repository.RequestMasterRowMapper;
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
	public RequestMasterVO templateRequest(@RequestBody  String request) {
		
		
		log.info(request);		
		
		RequestMasterVO req = templateRequestMessageService.transferRequest(request);
		
		return req;
		
//		return "Test";
		
	}
	

}
