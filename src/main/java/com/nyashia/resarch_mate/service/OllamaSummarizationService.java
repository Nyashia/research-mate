package com.nyashia.resarch_mate.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!mock-ai")
public class OllamaSummarizationService implements SummarizationService {

    private final ChatClient chatClient;

    public OllamaSummarizationService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Cacheable(value = "summaries" , key = "#paperId")
    public String summarize(String paperId, String abstractText) {

        return chatClient.prompt()
                .user("""
                        You are a research assistant. Summarize the following paper abstract
                        in 2-3 sentences of plain English.
                        Focus on the key contribution, not the background.

                        Abstract:
                        %s
                        """.formatted(abstractText))
                .call()
                .content();
    }
}