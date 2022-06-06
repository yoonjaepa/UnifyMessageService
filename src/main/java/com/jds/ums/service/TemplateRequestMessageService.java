package com.jds.ums.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.jds.ums.common.ErrorCode;
import com.jds.ums.common.JsonUtil;
import com.jds.ums.common.ValidationException;
import com.jds.ums.component.JsonSchemaValidator;
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
	
	@Autowired
	private JsonSchemaValidator validator;
	
	private String requestJson;
	private RequestMasterVO requestVo;
	
	/**
	 * 기본정보들을 세팅한다. 
	 * @param request
	 * @throws Exception, ValidationException 
	 */
	private void setRequest(String request) 
			throws Exception, ValidationException {
		
		try {
			if (requestValidator(request)) {
				this.requestJson = request;
			}	//	end if
			
		} catch (ValidationException e) {
			log.error(e.toString());

			throw e;
		} catch (Exception e) {
			log.error(e.toString());

			throw e;
		}	//	end try
		
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
	
	/**
	 * JSON Schema와 비교해서 결과를 반환한다. 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ValidationException
	 */
	private boolean requestValidator(String request) 
			throws Exception, ValidationException {
		
		try {
			ProcessingReport report = validator.jsonValidationProcess(request);
			if(report.isSuccess()) {
				return true;
			} else {
				log.error(report.toString());
				throw new ValidationException(report.toString());
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}	//	end try
		


	}	//	end requestValidator

	@Override
	public RequestMasterVO transferRequest(String request) {
		
		try {
			this.setRequest(request);
			this.registRequest();
			
			//	client 에서 요청한 내용을 MQ로 전달
			kafkaMessageSender.send(this.requestVo);
			
		} catch(ValidationException e) {
			log.error(e.toString());
			this.requestVo = new RequestMasterVO();
			this.requestVo.setErrorCode(ErrorCode.JsonSchemaValidationException.getErrorCode());
			this.requestVo.setErrorDetail(e.getMessage());
			
		} catch (Exception e) {
			log.error(e.toString());
			this.requestVo = new RequestMasterVO();
			this.requestVo.setErrorCode(ErrorCode.Exception.getErrorCode());
			this.requestVo.setErrorDetail(ErrorCode.Exception.getErrorName());
		}	//	end try		
			
		return this.requestVo;
		
		
	}
	

	

}
