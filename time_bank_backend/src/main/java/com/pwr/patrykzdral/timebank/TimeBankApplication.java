package com.pwr.patrykzdral.timebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimeBankApplication {
	public static final String DATABASE_PATH = "C:\\Users\\Patryk Zdral\\Documents\\database.xml";
	public static void main(String[] args) {
		SpringApplication.run(TimeBankApplication.class, args);
	}
}
