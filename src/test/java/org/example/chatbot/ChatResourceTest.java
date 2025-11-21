package org.example.chatbot;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.example.chatbot.dto.ChatRequest;
import org.example.chatbot.dto.ChatResponse;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChatResourceTest {

    @Test
    @Order(1)
    public void testChatMemoryAcrossMultipleCalls() {

        String memoryId = "memory-test-123";

        ChatRequest firstRequest = new ChatRequest("My name is John");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(firstRequest)
                .when()
                .post("/v1/api/llm/chatbot/" + memoryId)
                .then()
                .statusCode(200);


        ChatRequest secondRequest = new ChatRequest("What is my name?");

        ChatResponse response =given()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(secondRequest)
                .when()
                .post("/v1/api/llm/chatbot/" + memoryId)
                .then()
                .statusCode(200)
                .extract().as(ChatResponse.class);

        System.out.println("response: " + response.message());
        Assertions.assertTrue(response.message().contains("John"));
    }
}
