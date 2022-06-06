package com.jds.ums;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.jds.ums.component.JsonSchemaValidator;

import lombok.extern.slf4j.Slf4j;



@SpringBootTest
@Slf4j
class TestJsonSchemaValidator {

	String request;
	
	@Autowired
	JsonSchemaValidator validator;
	
	
	@Test
	void TestJsonValidationProcess() {
		
		try {
			ProcessingReport report = validator.jsonValidationProcess(request);
			log.info(report.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		
	}
	
	//@Ignore
	@Test
	void TestRequestJsonValidator() {
		
		JsonNode schemaNode = getJsonSchemaFromFile();
		JsonNode requestNode = null;
		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		
		ProcessingReport report = null;
				
		try {
			requestNode = new ObjectMapper().readTree(request);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			JsonSchema schema = factory.getJsonSchema(schemaNode);
			
			report = schema.validate(requestNode);
			log.info(report.toString());
			
			
			
		} catch (ProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
	}
	
	JsonNode getJsonSchemaFromFile() {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			Path path = Paths.get(ClassLoader
					.getSystemResource("request_schema.json").toURI());
			return mapper.readTree(
					Files.readAllLines(path).stream()
					.collect(Collectors.joining()));
		} catch (IOException | URISyntaxException e) {
			
			
		}	
		
		return null;	
	}
	
	@BeforeEach
	void setRequest() {
		this.request = "{\n"
				+ "  \"channelUid\":\"3f0025e3-d344-11ec-b1b3-0242ac120002\",\n"
				+ "  \"bizCustUid\":\"3f0025e3-d344-11ec-b1b3-0242ac120002\",\n"
				+ "  \"templateCd\":\"T0001\",\n"
				+ "  \"isTemplate\":true,\n"
				+ "  \"isMass\":true,\n"
				+ "  \"requestChannelIp\":\"0.0.0.0\",\n"
				+ "  \"messageCount\":1,\n"
				+ "  \"reservedStr1\":\"bcd\",\n"
				+ "  \"reservedStr2\":\"abc\",\n"
				+ "  \"requestDetail\":[{\n"
				+ "	   \"messageNo\":1,\n"
				+ "    \"mediaTypeCd\":\"MD001\",\n"
				+ "    \"templateCd\":\"TP001\",\n"
				+ "    \"messageTitle\":\"초대메일\",\n"
				+ "    \"receiverInfo\":{\n"
				+ "  		\"receiverEmail\":\"bal0601@kt.com\",\n"
				+ "      	\"receiverName\":\"송정근\",\n"
				+ " 	 	\"pw\":\"new1234\"  \n"
				+ "    	},\n"
				+ "	   \"templateVariable\":{\n"
				+ "			\"custName\":\"송정근\",\n"
				+ "			\"custEmail\":\"bal0601@kt.com\",\n"
				+ "			\"custTelNo\":\"010-9999-9999\"\n"
				+ "		},\n"
				+ "    \"senderInfo\":{\n"
				+ "      	\"senderName\":\"MetaLounge\",\n"
				+ "      	\"senderEmail\":\"thundermail@andwise.com\",\n"
				+ "      	\"returnEmail\":\"thundermail@andwise.com\"\n"
				+ "    	}\n"
				+ "	},\n"
				+ "	{\n"
				+ "	   \"messageNo\":2,\n"
				+ "    \"mediaTypeCd\":\"MD001\",\n"
				+ "    \"templateCd\":\"TP001\",\n"
				+ "    \"messageTitle\":\"초대메일\",\n"
				+ "    \"receiverInfo\":{\n"
				+ "  		\"receiverEmail\":\"yoonjaepa@gmail.com\",\n"
				+ "      	\"receiverName\":\"강정근\",\n"
				+ " 	 	\"pw\":\"new1234\"  \n"
				+ "    },\n"
				+ "		\"templateVariable\":{\n"
				+ "		\"custName\":\"송정근\",\n"
				+ "		\"custEmail\":\"yoonjaepa@gmail.com\",\n"
				+ "		\"custTelNo\":\"010-9902-0500\"\n"
				+ "		},\n"
				+ "    \"senderInfo\":{\n"
				+ "      	\"senderName\":\"MetaLounge\",\n"
				+ "      	\"senderEmail\":\"thundermail@andwise.com\",\n"
				+ "      	\"returnEmail\":\"thundermail@andwise.com\"\n"
				+ "    	}      \n"
				+ "	}\n"
				+ "	]\n"
				+ "}";
		
	}
	

}
