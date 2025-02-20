package com.dozie.ai_assistant.dto;

import lombok.Data;

@Data
public class AssistantRequest {
    private String content;
    private String operation;
}
