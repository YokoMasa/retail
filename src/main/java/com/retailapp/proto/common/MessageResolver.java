package com.retailapp.proto.common;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class MessageResolver {
    
    @Autowired
    private MessageSource messageSource;

    public String get(String messageId, Object... args) {
        return messageSource.getMessage(messageId, args, Locale.JAPANESE);
    }

}
