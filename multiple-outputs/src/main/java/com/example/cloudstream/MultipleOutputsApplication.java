package com.example.cloudstream;

import com.example.cloudstream.processor.MyProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@EnableBinding(MyProcessor.class)
public class MultipleOutputsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipleOutputsApplication.class, args);
	}

	@Autowired
	private MyProcessor myProcessor;

	@StreamListener(MyProcessor.INPUT)
	public void routeValues(Integer val) {
		System.out.println("MultipleOutput Received routeValues: " + val);
		if (val < 10) {
			System.out.println("send to anOutput");
			myProcessor.anOutput()
					.send(message(val));
		} else {
			System.out.println("send to anotherOutput");
			myProcessor.anotherOutput()
					.send(message(val));
		}
	}

	private static final <T> Message<T> message(T val) {
		return MessageBuilder.withPayload(val)
				.build();
	}
}
