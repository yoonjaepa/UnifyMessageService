package com.jds.ums.sql;

import java.time.LocalDateTime

class RequestSql {

	public static final String SELECT_REQUEST = """
					SELECT * FROM TB_REQUEST_MASTER;
	""";
	
	
	public static final String INSERT_REQUEST_MASTER = """
				INSERT INTO TB_REQ_MSG_MST
					(
						REQ_MST_UID, 
						CHNNL_UID, 
						BIZCUST_UID, 
						REQ_RCV_DTTM, 
						TMPLT_YN, 
						MASS_YN, 
						REQ_CHNNL_IP, 
						MSG_CNT, 
						REQ_EDOC, 
						RSVRD_FLD_1, 
						RSVRD_FLD_2, 
						REQ_STTS_CD, 
						REQ_STTS_MDFY_DTTM, 
						ERR_CD, 
						ERR_DTL, 
						REQ_DATE, 
						REQ_ID, 
						MDFY_DATE, 
						MDFY_ID
					)
				VALUES
					(
						:requestMasterUid
						,:channelUid
						,:bizCustUid          
						,:requestRecieveDttm  
						,:isTemplate              
						,:isMass        
						,:requestChannelIp           
						,:messageCount            
						,:requestDoc             
						,:reservedStr1            
						,:reservedStr2          
						,:requestStatusCd
						,:requestStatusChangeDttm
						,:errorCode
						,:errorDetail
						,:registDttm
						,:registId
						,:modifyDttm
						,:modifyId
					);

	""";
	

	public static final String INSERT_REQUEST_DTL = """
			
				INSERT INTO TB_REQ_MSG_DTL
						(
						REQ_MST_UID, 
						REQ_DTL_UID, 
					    MSG_NO,
						MEDIA_TYPE_CD, 
						TMPLT_CD, 
						MSG_TITLE, 
						RCVR_INFO, 
						TMPLT_VAR, 
						MSG_CONT, 
						REQ_DATE, 
						REQ_ID, 
						MDFY_DATE, 
						MDFY_ID
						)
				VALUES
						(
						:requestMasterUid  
						,:requestDetailUid 
    					,:messageNo 
						,:mediaTypeCd       
						,:templateCd        
						,:messageTitle      
						,:recieverInfo      
						,:templateVariable  
						,:messageContents   
						,:registDttm
						,:registId  
						,:modifyDttm
						,:modifyId  
						);
	""";

	
	}
	
	
	