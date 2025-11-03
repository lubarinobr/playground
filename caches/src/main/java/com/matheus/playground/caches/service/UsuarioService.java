package com.matheus.playground.caches.service;

import com.matheus.playground.caches.dto.CreateUsuarioDTO;
import com.matheus.playground.caches.dto.UpdateUsuarioDTO;
import com.matheus.playground.caches.dto.UsuarioDTO;
import com.matheus.playground.caches.model.Usuario;
import com.matheus.playground.caches.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
        return toDTO(usuario);
    }

    @Transactional
    public UsuarioDTO create(CreateUsuarioDTO dto) {
        var usuario = new Usuario(dto.nome(), dto.email());
        var saved = usuarioRepository.save(usuario);
        return toDTO(saved);
    }

    @Transactional
    public UsuarioDTO update(Long id, UpdateUsuarioDTO dto) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
        
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setDataAtualizacao(java.time.LocalDateTime.now());
        
        var updated = usuarioRepository.save(usuario);
        return toDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCriacao(),
                usuario.getDataAtualizacao()
        );
    }
}

