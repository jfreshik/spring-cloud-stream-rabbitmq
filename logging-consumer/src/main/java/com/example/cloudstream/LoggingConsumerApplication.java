package com.example.cloudstream;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class LoggingConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoggingConsumerApplication.class, args);
	}

	@Bean
	public Consumer<Person> log() {
		return person -> {
			System.out.println("Received: " + person);
		};
	}

	@Getter
	@Setter
	public static class Person {
		private String name;

		@Override
		public String toString() {
			return this.name;
		}
	}
}
