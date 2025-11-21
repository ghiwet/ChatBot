package org.example.chatbot.resouces;

import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.chatbot.services.MyAiService;
import org.jboss.resteasy.reactive.RestPath;

@Path("/v1/api/llm/chatbot")
public class ChatbotResource {

    @Inject
    MyAiService myAiService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{message}")
    @Blocking
    public Response chat(@RestPath("message") String message) {

        String response = myAiService.chat(message);
        JsonObject json = new JsonObject()
                .put("response", response);

        return Response.ok(json).build();

    }
}
