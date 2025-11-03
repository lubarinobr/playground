package com.matheus.playground.caches.dto;

import java.time.LocalDateTime;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
}

