package org.example.chatbot.resouces;

import dev.langchain4j.store.embedding.IngestionResult;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.chatbot.dto.ChatRequest;
import org.example.chatbot.dto.ChatResponse;
import org.example.chatbot.dto.IngestionRequest;
import org.example.chatbot.services.IngestionService;
import org.example.chatbot.services.MyAiService;
import org.jboss.resteasy.reactive.RestPath;

import java.util.Objects;

@Path("/v1/api/llm/chatbot")
public class ChatbotResource {

    @Inject
    MyAiService myAiService;

    @Inject
    IngestionService ingestionService;

    @POST
    @Path("/{memoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    public Response chat(@RestPath("memoryId") String memoryId, ChatRequest request) {

        String response = myAiService.chat(memoryId, request.message());
        ChatResponse chatResponse = new ChatResponse(response);

        return Response.ok(chatResponse).build();
    }

    @POST
    @Path("/ingest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    public Response documentIngestion(IngestionRequest request) {


        IngestionResult ingestionResult = ingestionService.ingest(request.documents(), request.userId());
        if(Objects.nonNull(ingestionResult)) {
            return Response.ok().build();
        }
        else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ingestion failed").build();
        }
    }
}
