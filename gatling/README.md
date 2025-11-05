# Gatling Load Testing Project

Projeto de teste de carga usando Gatling para validar o desempenho das APIs REST.

## Estrutura do Projeto

```
gatling/
├── pom.xml
├── src/
│   └── test/
│       ├── scala/
│       │   └── com/matheus/playground/gatling/
│       │       ├── DocumentoSimulation.scala
│       │       ├── UsuarioSimulation.scala
│       │       └── BuscarDocumentoSimulation.scala
│       └── resources/
│           └── gatling.conf
```

## Simulações Disponíveis

### DocumentoSimulation
Testa a API de documentos (`/documentos`) com os seguintes cenários:
- **Cenário Completo**: Criação de usuário, criação de documento, listagem, busca, atualização e exclusão
- **Cenário Leitura**: Teste de leitura contínua (listagem e busca por ID)

### UsuarioSimulation
Testa a API de usuários (`/usuarios`) com os seguintes cenários:
- **Cenário Completo**: Criação, listagem, busca, atualização e exclusão
- **Cenário Leitura**: Teste de leitura contínua
- **Cenário Criação**: Teste focado em criação de usuários

### BuscarDocumentoSimulation
Testa especificamente o endpoint de busca de documentos (`GET /documentos/{id}`) com os seguintes cenários:
- **Busca Intensiva**: Teste de carga crescente até 20 requisições/segundo
- **Busca Sustentada**: Teste de carga constante de 5 requisições/segundo por 2 minutos
- **Busca com Pico**: Simula picos súbitos de 100 usuários simultâneos

Este teste é ideal para validar o desempenho do cache Redis quando há muitas buscas pelo mesmo documento.

## Pré-requisitos

- Java 21
- Maven 3.6+
- Aplicação backend rodando em `http://localhost:8080`

## Como Executar

### Executar todas as simulações

```bash
cd gatling
mvn clean gatling:test
```

### Executar uma simulação específica

```bash
cd gatling
mvn clean gatling:test -Dgatling.simulationClass=com.matheus.playground.gatling.DocumentoSimulation
```

ou

```bash
cd gatling
mvn clean gatling:test -Dgatling.simulationClass=com.matheus.playground.gatling.UsuarioSimulation
```

ou para teste focado na busca de documentos:

```bash
cd gatling
mvn clean gatling:test -Dgatling.simulationClass=com.matheus.playground.gatling.BuscarDocumentoSimulation
```

### Executar apenas a compilação (sem rodar os testes)

```bash
cd gatling
mvn clean test-compile
```

## Resultados

Os relatórios de teste são gerados em:
```
gatling/target/gatling/[simulation-name]-[timestamp]/index.html
```

Abra o arquivo `index.html` no navegador para visualizar os resultados detalhados.

## Configurações

As configurações podem ser ajustadas no arquivo `src/test/resources/gatling.conf`.

### Ajustar URL Base

Para testar contra outra URL, modifique o `baseUrl` nas simulações:

```scala
val httpProtocol = http
  .baseUrl("http://seu-servidor:porta")
```

### Ajustar Cenários de Carga

Edite as simulações para ajustar:
- Número de usuários simultâneos
- Taxa de requisições por segundo
- Duração dos testes
- Tempo de pausa entre requisições

Exemplo:

```scala
setUp(
  cenarioCompleto.inject(
    rampUsers(10).during(30.seconds),           // 10 usuários em 30 segundos
    constantUsersPerSec(2).during(60.seconds), // 2 usuários/segundo por 60 segundos
    rampUsersPerSec(2).to(5).during(30.seconds) // de 2 para 5 usuários/segundo em 30 segundos
  )
)
```

### Assertions

As simulações incluem assertions para validar:
- Taxa de sucesso mínima (95%)
- Tempo de resposta máximo
- Tempo de resposta médio

## Exemplos de Padrões de Carga

### Teste de Stress
Aumente gradualmente o número de usuários até encontrar o ponto de quebra.

### Teste de Spike
Simule picos de tráfego repentinos.

### Teste de Endurance
Mantenha uma carga constante por um período prolongado.

### Teste de Volume
Processe um grande volume de dados.

## Integração com CI/CD

O Gatling pode ser integrado em pipelines CI/CD. Os relatórios HTML podem ser publicados como artefatos.

