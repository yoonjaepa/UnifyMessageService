package com.jds.ums.service;


import com.jds.ums.vo.RequestMasterVO;

public interface IFRequestMessageService {
	
	public boolean 	validateRequest(RequestMasterVO request);
	public String	registRequest(RequestMasterVO request);
	public void 	transferRequest(RequestMasterVO request);


}
