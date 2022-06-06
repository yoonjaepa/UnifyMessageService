package com.jds.ums.component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JsonSchemaValidator {
	
	
	public ProcessingReport jsonValidationProcess(String json) throws Exception {
		
		JsonNode schemaNode = getJsonSchemaFromFile();
		JsonNode requestNode = null;
		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		
		ProcessingReport report = null;
		
		try {
			requestNode = new ObjectMapper().readTree(json);
			JsonSchema schema = factory.getJsonSchema(schemaNode);
			
			report = schema.validate(requestNode);
			
			log.info(report.toString());
			
			return report;
			
		} catch (JsonProcessingException|ProcessingException e) {
			log.error(e.toString());
			throw e;
		}	//	end try
		
		
		
	}	//	end jsonValidationProcess()
	
	
	private JsonNode getJsonSchemaFromFile() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			Path path = Paths.get
					(ClassLoader.getSystemResource("request_schema.json").toURI());
			
			return mapper.readTree(Files.readAllLines(path).stream()
					.collect(Collectors.joining()));
					
		} catch (IOException | URISyntaxException e) {
			log.error(e.toString());
			throw e;
			
		}	//	end try
		
		
		
	}	//	end getJsonSchemaFromFile
	
}
