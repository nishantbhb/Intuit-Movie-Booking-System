package com.intuit.movie.IntuitMovieBookingSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.intuit.movie")
@SpringBootApplication
public class IntuitMovieBookingSystemApplication implements CommandLineRunner {

	@Autowired
	ClientDriver cd;

	public static void main(String[] args) {
		SpringApplication.run(IntuitMovieBookingSystemApplication.class, args);

	}

	@Override
	public void run(String... arg0) {

		cd.PrintChoice();
	}
}
