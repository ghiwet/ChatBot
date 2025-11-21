package org.example.chatbot.services;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
@SystemMessage("You are a professional software developer")
@ApplicationScoped
public interface MyAiService {

    String chat(@UserMessage String message);
}
