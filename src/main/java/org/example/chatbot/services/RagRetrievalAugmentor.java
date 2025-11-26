package org.example.chatbot.services;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.function.Supplier;
@ApplicationScoped
public class RagRetrievalAugmentor implements Supplier<RetrievalAugmentor> {

    @ConfigProperty(name = "rag.retrieval.max-results", defaultValue = "5")
    int maxResults;

    @Inject
    EmbeddingModel embeddingModel;
    @Inject
    EmbeddingStore<TextSegment> embeddingStore;

    @Override
    public RetrievalAugmentor get() {
        EmbeddingStoreContentRetriever retriever =
                EmbeddingStoreContentRetriever.builder()
                        .embeddingModel(embeddingModel)
                        .embeddingStore(embeddingStore)
                        .maxResults(maxResults)
                        .build();
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(retriever)
                .build();
    }
}