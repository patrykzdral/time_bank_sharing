package com.pwr.patrykzdral.timebank;

import com.pwr.patrykzdral.timebank.file_read_write.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimeBankApplication {
	public static final String DATABASE_PATH = "C:\\Users\\Patryk Zdral\\Documents\\database.xml";
	public static Database databaseInMemory = new Database();

	public static void main(String[] args) {
		SpringApplication.run(TimeBankApplication.class, args);
	}
}
