package org.example.chatbot.services;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.chatbot.dto.UserDocument;

import java.util.*;

@ApplicationScoped
public class IngestionService {

    @Inject
    EmbeddingStoreIngestor ingestor;

    public IngestionResult ingest(List<UserDocument> userDocuments, String userId) {
        List<Document> documents = userDocuments.stream()
                        .map(userDocument -> {
                            Map<String, Object> metaDataMap = new HashMap<>();
                            metaDataMap.put("userId", userId);
                            if(Objects.nonNull(userDocument.docId())) {
                                metaDataMap.put("docId", userDocument.docId());
                            }
                            if(Objects.nonNull(userDocument.fileName())) {
                                metaDataMap.put("fileName", userDocument.fileName());
                            }
                            return Document.from(userDocument.text(),
                                    Metadata.from(metaDataMap));
                        }).toList();

        return ingestor.ingest(documents);
    }
}
