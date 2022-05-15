package com.jds.ums.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.jds.ums.sql.RequestSql;
import com.jds.ums.vo.RequestDetailVO;
import com.jds.ums.vo.RequestMasterVO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class RequestRepo {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public RequestRepo (NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		
	}
	
	public List<RequestMasterVO> findList(){
		
		
		
		
		return null;
	}
	
	public int registRequestMaster(RequestMasterVO request) {
		
		int cnt =0;
		
		SqlParameterSource param =new BeanPropertySqlParameterSource(request);
		
		cnt = namedParameterJdbcTemplate.update(RequestSql.INSERT_REQUEST_MASTER,param);
		
		return cnt;
				
	}
	
	public int[] registRequestDetail(RequestDetailVO[] details) {
		
		int[] cnt ;
		
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(details);
		
		cnt = namedParameterJdbcTemplate.batchUpdate(RequestSql.INSERT_REQUEST_DTL, params);
		
		return cnt;
	}	//	end registRequestDetail()
	
	
	
	
	
}
