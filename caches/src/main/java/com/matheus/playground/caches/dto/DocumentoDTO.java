package com.matheus.playground.caches.dto;

import java.time.LocalDateTime;

public record DocumentoDTO(
        Long id,
        String titulo,
        String conteudo,
        Long usuarioId,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
}

