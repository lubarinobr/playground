package com.matheus.playground.mcp.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AiService {

    public Mono<String> generateResponse(String prompt) {
        return Mono.fromCallable(() -> {
            // Simulação de resposta de IA
            return "Resposta simulada para: " + prompt;
        });
    }

    public Mono<String> generateResponseWithTemplate(String template, Map<String, Object> variables) {
        return Mono.fromCallable(() -> {
            // Simulação de resposta com template
            String result = template;
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                result = result.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
            return "Resposta simulada: " + result;
        });
    }
}