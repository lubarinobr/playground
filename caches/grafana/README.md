# Configuração do Grafana

Esta pasta contém a configuração de provisioning do Grafana para visualização de métricas do Redis e PostgreSQL.

## Estrutura

```
grafana/
├── provisioning/
│   ├── datasources/
│   │   └── prometheus.yml      # Configuração da fonte de dados Prometheus
│   └── dashboards/
│       ├── dashboards.yml       # Configuração de provisioning dos dashboards
│       ├── redis-dashboard.json # Dashboard para métricas do Redis
│       └── postgres-dashboard.json # Dashboard para métricas do PostgreSQL
```

## Dashboards

### Redis - Memória e Disco
Este dashboard exibe:
- Uso de memória Redis (usado vs máximo)
- Memória Redis ao longo do tempo
- Percentual de uso de memória
- Quantidade de chaves no Redis
- Informações detalhadas de memória

### PostgreSQL - Memória e Disco
Este dashboard exibe:
- Tamanho dos bancos de dados
- Uso atual de disco
- Conexões ativas
- Blocos em cache vs disco
- Taxa de cache hit
- Transações por segundo
- Informações sobre tabelas e índices

## Acesso

Após iniciar os containers com `docker-compose up`, acesse:
- **Grafana**: http://localhost:3000
- **Usuário**: admin
- **Senha**: admin

Os dashboards serão carregados automaticamente e estarão disponíveis no menu "Dashboards".

## Verificação

Para verificar se as métricas estão sendo coletadas:
1. Acesse o Prometheus: http://localhost:9090
2. Verifique os targets em Status > Targets
3. Execute queries como `redis_memory_used_bytes` ou `pg_database_size_bytes`

