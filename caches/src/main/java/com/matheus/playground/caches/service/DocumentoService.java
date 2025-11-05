package com.matheus.playground.caches.service;

import com.matheus.playground.caches.config.RedisConfig;
import com.matheus.playground.caches.dto.CreateDocumentoDTO;
import com.matheus.playground.caches.dto.DocumentoDTO;
import com.matheus.playground.caches.dto.UpdateDocumentoDTO;
import com.matheus.playground.caches.model.Documento;
import com.matheus.playground.caches.model.Usuario;
import com.matheus.playground.caches.repository.DocumentoRepository;
import com.matheus.playground.caches.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoService {

    private static final String CACHE_KEY_ALL = "documentos:all";
    private static final String CACHE_KEY_BY_ID = "documentos:id:";
    private static final String CACHE_KEY_BY_USUARIO = "documentos:usuario:";
    private static final Duration TTL = RedisConfig.getDefaultTtl();

    private final DocumentoRepository documentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CacheService cacheService;

    public DocumentoService(DocumentoRepository documentoRepository, 
                          UsuarioRepository usuarioRepository,
                          CacheService cacheService) {
        this.documentoRepository = documentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cacheService = cacheService;
    }

    @Transactional(readOnly = true)
    public List<DocumentoDTO> findAll() {
        var cached = cacheService.getList(CACHE_KEY_ALL, DocumentoDTO.class);
        if (cached.isPresent()) {
            return cached.get();
        }

        var documentos = documentoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        cacheService.put(CACHE_KEY_ALL, documentos, TTL);
        return documentos;
    }

    @Transactional(readOnly = true)
    public DocumentoDTO findById(Long id) {
        var cacheKey = CACHE_KEY_BY_ID + id;
        var cached = cacheService.get(cacheKey, DocumentoDTO.class);
        if (cached.isPresent()) {
            return cached.get();
        }

        var documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com id: " + id));
        
        var dto = toDTO(documento);
        cacheService.put(cacheKey, dto, TTL);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<DocumentoDTO> findByUsuarioId(Long usuarioId) {
        var cacheKey = CACHE_KEY_BY_USUARIO + usuarioId;
        var cached = cacheService.getList(cacheKey, DocumentoDTO.class);
        if (cached.isPresent()) {
            return cached.get();
        }

        var documentos = documentoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        cacheService.put(cacheKey, documentos, TTL);
        return documentos;
    }

    @Transactional
    public DocumentoDTO create(CreateDocumentoDTO dto) {
        var usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + dto.usuarioId()));
        var documento = new Documento(dto.titulo(), dto.conteudo(), usuario);
        var saved = documentoRepository.save(documento);
        
        cacheService.delete(CACHE_KEY_ALL);
        cacheService.delete(CACHE_KEY_BY_USUARIO + dto.usuarioId());
        var dtoResult = toDTO(saved);
        cacheService.put(CACHE_KEY_BY_ID + saved.getId(), dtoResult, TTL);
        
        return dtoResult;
    }

    @Transactional
    public DocumentoDTO update(Long id, UpdateDocumentoDTO dto) {
        var documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com id: " + id));
        
        var usuarioId = documento.getUsuario() != null ? documento.getUsuario().getId() : null;
        
        documento.setTitulo(dto.titulo());
        documento.setConteudo(dto.conteudo());
        documento.setDataAtualizacao(java.time.LocalDateTime.now());
        
        var updated = documentoRepository.save(documento);
        
        cacheService.delete(CACHE_KEY_ALL);
        cacheService.delete(CACHE_KEY_BY_ID + id);
        if (usuarioId != null) {
            cacheService.delete(CACHE_KEY_BY_USUARIO + usuarioId);
        }
        
        var dtoResult = toDTO(updated);
        cacheService.put(CACHE_KEY_BY_ID + id, dtoResult, TTL);
        
        return dtoResult;
    }

    @Transactional
    public void delete(Long id) {
        var documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com id: " + id));
        
        var usuarioId = documento.getUsuario() != null ? documento.getUsuario().getId() : null;
        
        documentoRepository.deleteById(id);
        
        cacheService.delete(CACHE_KEY_ALL);
        cacheService.delete(CACHE_KEY_BY_ID + id);
        if (usuarioId != null) {
            cacheService.delete(CACHE_KEY_BY_USUARIO + usuarioId);
        }
    }

    private DocumentoDTO toDTO(Documento documento) {
        return new DocumentoDTO(
                documento.getId(),
                documento.getTitulo(),
                documento.getConteudo(),
                documento.getUsuario() != null ? documento.getUsuario().getId() : null,
                documento.getDataCriacao(),
                documento.getDataAtualizacao()
        );
    }
}

