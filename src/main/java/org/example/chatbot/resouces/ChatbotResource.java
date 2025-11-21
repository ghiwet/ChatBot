package org.example.chatbot.resouces;

import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.chatbot.dto.ChatRequest;
import org.example.chatbot.dto.ChatResponse;
import org.example.chatbot.services.MyAiService;
import org.jboss.resteasy.reactive.RestPath;

@Path("/v1/api/llm/chatbot")
public class ChatbotResource {

    @Inject
    MyAiService myAiService;

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
}
