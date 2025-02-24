package com.sandbox.springboot;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TextItemProcessor implements ItemProcessor<String, String> {

    @Override
    @Nullable
    public String process(@NonNull String message) throws Exception {
        return message.concat(message).toUpperCase();
    }
}
