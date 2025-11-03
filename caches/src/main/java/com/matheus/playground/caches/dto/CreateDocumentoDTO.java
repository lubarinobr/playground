package com.matheus.playground.caches.dto;

public record CreateDocumentoDTO(
        String titulo,
        String conteudo,
        Long usuarioId
) {
}

