package com.dozie.ai_assistant.service;

import com.dozie.ai_assistant.dto.AssistantRequest;
import org.springframework.stereotype.Service;

public interface AssistantService {
    String processContent(AssistantRequest request);
}
