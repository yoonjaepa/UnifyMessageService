package com.jds.ums.vo;


import java.util.Map;

import org.apache.kafka.common.Uuid;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RequestDetailVO extends BaseAuditVO {
	
	private String 		requestMasterUid;
	private String		requestDetailUid;
	private int			messageNo;
	private String		mediaTypeCd;
	private String		templateCd;
	private String		messageTitle;
	private Map<String, String>	recieverInfo;
	private Map<String, String>	templateVariable;
	private String		messageContents;
	

	public RequestDetailVO() {
		
		setRequestDetailUid(Uuid.randomUuid().toString());
	}
	
	
	
}
