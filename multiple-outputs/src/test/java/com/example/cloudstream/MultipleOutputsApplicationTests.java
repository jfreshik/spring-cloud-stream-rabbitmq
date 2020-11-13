package com.example.cloudstream;

import com.example.cloudstream.processor.MyProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext
class MultipleOutputsApplicationTests {


	@Autowired
	private MyProcessor pipe;

	@Autowired
	private MessageCollector messageCollector;

	@Test
	public void whenSendMessage_thenResponseIsInAOutput(){
		whenSendMessage(1);
		thenPayloadInChannelIs(pipe.anOutput(), "1");
	}

	@Test
	public void whenSendMessage_thenResponseIsInAnotherOutput() {
		whenSendMessage(11);
		thenPayloadInChannelIs(pipe.anotherOutput(), "11");
	}


	private void whenSendMessage(Integer val) {
		pipe.myInput()
				.send(MessageBuilder.withPayload(val)
				.build());
	}

	private void thenPayloadInChannelIs(MessageChannel channel, String expectedValue) {
		Object payload = messageCollector.forChannel(channel)
				.poll()
				.getPayload();
		assertEquals(expectedValue, payload);
	}
}
