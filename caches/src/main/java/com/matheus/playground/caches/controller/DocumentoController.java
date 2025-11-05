package com.matheus.playground.caches.controller;

import com.matheus.playground.caches.dto.CreateDocumentoDTO;
import com.matheus.playground.caches.dto.DocumentoDTO;
import com.matheus.playground.caches.dto.UpdateDocumentoDTO;
import com.matheus.playground.caches.service.DocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @GetMapping
    public ResponseEntity<List<DocumentoDTO>> findAll() {
        return ResponseEntity.ok(documentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DocumentoDTO>> findByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(documentoService.findByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<DocumentoDTO> create(@RequestBody CreateDocumentoDTO dto) {
        var created = documentoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoDTO> update(@PathVariable Long id, @RequestBody UpdateDocumentoDTO dto) {
        return ResponseEntity.ok(documentoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

