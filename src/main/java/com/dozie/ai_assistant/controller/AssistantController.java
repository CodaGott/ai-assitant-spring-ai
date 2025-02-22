package com.dozie.ai_assistant.controller;

import com.dozie.ai_assistant.dto.AssistantRequest;
import com.dozie.ai_assistant.service.AssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/research")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssistantController {
    private final AssistantService assistantService;

    @PostMapping("/process")
    public ResponseEntity<String> processContent(@RequestBody AssistantRequest request) {
        String result = assistantService.processContent(request);
        return ResponseEntity.ok(result);
    }
}
