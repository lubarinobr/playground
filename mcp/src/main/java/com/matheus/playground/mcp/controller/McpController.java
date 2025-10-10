package com.matheus.playground.mcp.controller;

import com.matheus.playground.mcp.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/mcp")
@RequiredArgsConstructor
public class McpController {

    private final AiService aiService;

    @PostMapping("/chat")
    public Mono<String> chat(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        return aiService.generateResponse(prompt);
    }

    @PostMapping("/chat/template")
    public Mono<String> chatWithTemplate(@RequestBody Map<String, Object> request) {
        String template = (String) request.get("template");
        @SuppressWarnings("unchecked")
        Map<String, Object> variables = (Map<String, Object>) request.get("variables");
        return aiService.generateResponseWithTemplate(template, variables);
    }

    @GetMapping("/health")
    public Mono<Map<String, String>> health() {
        return Mono.just(Map.of("status", "UP", "module", "MCP"));
    }
}