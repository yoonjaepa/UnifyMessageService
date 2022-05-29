package com.jds.ums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TestConfigSet {

	@Value("${ums.test.text}")
	String server;
	
	@Test
	void test() {
		
		log.info("Server : " + server);
		
		
	}

}
