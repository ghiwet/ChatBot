package org.example.chatbot;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;

import java.util.Objects;

import static dev.langchain4j.data.document.splitter.DocumentSplitters.recursive;

@ApplicationScoped
public class RAGConfig {

    @Inject
    EmbeddingModel embeddingModel;

    @Produces
    @ApplicationScoped
    public EmbeddingStore<TextSegment> createStore() {
        return new InMemoryEmbeddingStore<>();
    }

    @Produces
    @ApplicationScoped
    public EmbeddingStoreIngestor createIngestionStore(EmbeddingStore<TextSegment> store) {
        return EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .documentSplitter(recursive(500, 30))
                .textSegmentTransformer(textSegment -> {
                    String text = textSegment.text();
                    String fileName = textSegment.metadata().getString("fileName");
                    if(Objects.nonNull(fileName) && !fileName.isBlank()) {
                        text = fileName + "\n" + text;
                    }

                    return TextSegment.from(text,
                            textSegment.metadata());
                })
                .build();
    }
}
