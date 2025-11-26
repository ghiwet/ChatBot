package org.example.chatbot.dto;

import java.util.List;

public record IngestionRequest(List<UserDocument> documents, String userId) {
}
