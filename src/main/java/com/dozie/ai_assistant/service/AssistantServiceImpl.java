package com.dozie.ai_assistant.service;

import com.dozie.ai_assistant.dto.AssistantRequest;
import com.dozie.ai_assistant.dto.GeminiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class AssistantServiceImpl implements AssistantService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    public AssistantServiceImpl(WebClient.Builder webClient, ObjectMapper objectMapper) {
        this.webClient = webClient.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public String processContent(AssistantRequest request) {
        //TODO : Build the prompt
        String prompt = buildPrompt(request);

        //TODO : Call the AI API
        Map<String , Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                });
        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //TODO : Parse the response
        //TODO : Return the response
        return extractTextFromResponse(response);
    }

    private String extractTextFromResponse(String response) {
        try {
            GeminiResponse geminiResponse = objectMapper.readValue(response, GeminiResponse.class);
            if (geminiResponse.getCandidates() != null && !geminiResponse.getCandidates().isEmpty()) {
                GeminiResponse.Candidate firstCandidate = geminiResponse.getCandidates().get(0);
                if (firstCandidate.getContent() != null &&
                        firstCandidate.getContent().getParts() != null &&
                        !firstCandidate.getContent().getParts().isEmpty()) {
                    return firstCandidate.getContent().getParts().get(0).getText();
                }
            }
            return "No Content Found in response body";
        }catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String buildPrompt(AssistantRequest request) {
        StringBuilder prompt = new StringBuilder();

        switch (request.getOperation()){
            case "summarize":
                prompt.append("Provide a clear and concise summary of the following text in a few sentences:\n\n");
                break;
            case "suggest":
                prompt.append("Based on the following content: suggest related topics and further reading. Format the response with clear headings and bullet points:\n\n");
                break;
            case "teach":
                prompt.append("Based on the following content: teach me about the topic in a clear to understand language. Format the response with clear headings and bullet points:\n\n");
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + request.getOperation());
        }
        prompt.append(request.getContent());
        return prompt.toString();
    }
}