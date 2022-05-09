package com.jds.ums.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jds.ums.vo.RequestMasterVO;

public class RequestMasterRowMapper implements RowMapper<RequestMasterVO> {

	@Override
	public RequestMasterVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		RequestMasterVO mst		= 	new RequestMasterVO();
		
		/*
		 * mst.setRequestMasterUid(rs.getString("REQ_MST_UID"));
		 * mst.setRequestRecieveDttm(rs.getTimestamp("REQ_RCV_DTTM").toLocalDateTime());
		 * mst.setRequestChannelId(rs.getString("REQ_CHNNL_ID"));
		 * mst.setIsTemplate(rs.getBoolean("IS_TMPLT"));
		 * mst.setIsMass(rs.getBoolean("IS_MASS"));
		 * mst.setTemplateId(rs.getString("TMPLT_ID"));
		 * mst.setMediaTypeCd(rs.getString("MEDIA_TP_CD"));
		 * mst.setEndUserIp(rs.getString("END_USR_ID"));
		 * mst.setMessageCnt(rs.getInt("MSG_CNT"));
		 * mst.setReservedStr1(rs.getString("RESERV_1"));
		 * mst.setReservedStr2(rs.getString("RESERV_2"));
		 * mst.setRequestStatusCd(rs.getString("REQ_STTS_CD"));
		 * mst.setRequestStatusChangeDttm(rs.getTimestamp("REQ_STTS_CHG_DTTM").
		 * toLocalDateTime());
		 */

		
		//	audit columns
		mst.setRegistDttm(rs.getTimestamp("REG_DATE").toLocalDateTime());
		mst.setRegistId(rs.getString("REG_ID"));
		mst.setModifyDttm(rs.getTimestamp("MDFY_DTTM").toLocalDateTime());
		mst.setModifyId(rs.getString("MDFY_ID"));
		
		
		return mst;
	}

}
