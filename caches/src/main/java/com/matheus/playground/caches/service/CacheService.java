package com.matheus.playground.caches.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public <T> Optional<T> get(String key, Class<T> clazz) {
        try {
            var value = redisTemplate.opsForValue().get(key);
            if (value == null) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.convertValue(value, clazz));
        } catch (Exception e) {
            logger.warn("Redis indisponível ao buscar chave '{}': {}. Consultando banco de dados.", key, e.getMessage());
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<List<T>> getList(String key, Class<T> clazz) {
        try {
            var value = redisTemplate.opsForValue().get(key);
            if (value == null) {
                return Optional.empty();
            }
            if (value instanceof List<?> list) {
                return Optional.of((List<T>) list);
            }
            try {
                var typeRef = new TypeReference<List<T>>() {};
                var json = objectMapper.writeValueAsString(value);
                var list = objectMapper.readValue(json, typeRef);
                return Optional.of(list);
            } catch (Exception e) {
                logger.warn("Erro ao deserializar lista do cache para chave '{}': {}", key, e.getMessage());
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.warn("Redis indisponível ao buscar lista da chave '{}': {}. Consultando banco de dados.", key, e.getMessage());
            return Optional.empty();
        }
    }

    public void put(String key, Object value, Duration ttl) {
        try {
            redisTemplate.opsForValue().set(key, value, ttl);
        } catch (Exception e) {
            logger.warn("Redis indisponível ao salvar chave '{}': {}. Continuando sem cache.", key, e.getMessage());
        }
    }

    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.warn("Redis indisponível ao deletar chave '{}': {}. Continuando sem cache.", key, e.getMessage());
        }
    }

    public void deletePattern(String pattern) {
        try {
            var keys = redisTemplate.keys(pattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            logger.warn("Redis indisponível ao deletar padrão '{}': {}. Continuando sem cache.", pattern, e.getMessage());
        }
    }
}
