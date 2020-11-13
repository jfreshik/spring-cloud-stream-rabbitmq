package com.example.cloudstream;

import com.example.cloudstream.messages.TextPlainMessageConverter;
import com.example.cloudstream.model.LogMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
@EnableBinding(Processor.class)
public class MyLoggerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyLoggerServiceApplication.class, args);
	}

	@Bean
	public MessageConverter providesTextPlainMessageConverter() {
		return new TextPlainMessageConverter();
	}

	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public LogMessage enrichLogMessage(LogMessage log) {
		System.out.println("StreamListener[Processor.INPUT]: " + log.toString());
		return new LogMessage(String.format("[1]: %s", log.getMessage()));
	}
}
