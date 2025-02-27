package com.example.sap1701_team1.fptmentorlink;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
public class FPTMentorLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(FPTMentorLinkApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner initData(){
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//
//			}
//		}
//	}
}
