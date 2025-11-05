package com.matheus.playground.caches.repository;

import com.matheus.playground.caches.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByUsuarioId(Long usuarioId);
}

