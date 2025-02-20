package com.dozie.ai_assistant.service;

import com.dozie.ai_assistant.dto.AssistantRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AssistantServiceImpl implements AssistantService {


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
        //TODO : Parse the response
        //TODO : Return the response
        return "";
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
            default:
                throw new IllegalArgumentException("Unknown operation: " + request.getOperation());
        }
        prompt.append(request.getContent());
        return prompt.toString();
    }
}
