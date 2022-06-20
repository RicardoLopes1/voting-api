package br.com.solutis.votingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class VotingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingApiApplication.class, args);
	}

}
