package com.example.cloudstream.messages;

import com.example.cloudstream.model.LogMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

public class TextPlainMessageConverter extends AbstractMessageConverter {
    public TextPlainMessageConverter() {
        super(new MimeType("text", "plain"));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return LogMessage.class == aClass;
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        Object payload = message.getPayload();
        String text = payload instanceof String ? (String) payload : new String((byte[]) payload);
        return new LogMessage(text);
    }


}
