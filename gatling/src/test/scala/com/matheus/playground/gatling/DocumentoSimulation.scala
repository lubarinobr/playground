package com.matheus.playground.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class DocumentoSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
    .userAgentHeader("Gatling Load Test")

  val criarUsuarioFeeder = Iterator.continually(Map(
    "nome" -> s"Usuario_${System.currentTimeMillis()}",
    "email" -> s"usuario_${System.currentTimeMillis()}@example.com"
  ))

  val criarDocumentoFeeder = Iterator.continually(Map(
    "titulo" -> s"Documento_${System.currentTimeMillis()}",
    "conteudo" -> s"Conteudo do documento ${System.currentTimeMillis()}"
  ))

  val criarUsuario = exec(
    http("Criar Usuario")
      .post("/usuarios")
      .body(StringBody("""{"nome": "${nome}", "email": "${email}"}"""))
      .check(status.is(201))
      .check(jsonPath("$.id").saveAs("usuarioId"))
  )

  val criarDocumento = exec(
    http("Criar Documento")
      .post("/documentos")
      .body(StringBody("""{"titulo": "${titulo}", "conteudo": "${conteudo}", "usuarioId": ${usuarioId}}"""))
      .check(status.is(201))
      .check(jsonPath("$.id").saveAs("documentoId"))
  )

  val listarDocumentos = exec(
    http("Listar Documentos")
      .get("/documentos")
      .check(status.is(200))
  )

  val buscarDocumentoPorId = exec(
    http("Buscar Documento por ID")
      .get("/documentos/${documentoId}")
      .check(status.in(200, 404))
  )

  val atualizarDocumento = exec(
    http("Atualizar Documento")
      .put("/documentos/${documentoId}")
      .body(StringBody("""{"titulo": "Documento Atualizado", "conteudo": "Conteudo atualizado"}"""))
      .check(status.in(200, 404))
  )

  val deletarDocumento = exec(
    http("Deletar Documento")
      .delete("/documentos/${documentoId}")
      .check(status.in(204, 404))
  )

  val cenarioCompleto = scenario("Cenario Completo Documentos")
    .feed(criarUsuarioFeeder)
    .exec(criarUsuario)
    .pause(1.second)
    .feed(criarDocumentoFeeder)
    .exec(criarDocumento)
    .pause(1.second)
    .exec(listarDocumentos)
    .pause(1.second)
    .exec(buscarDocumentoPorId)
    .pause(1.second)
    .exec(atualizarDocumento)
    .pause(1.second)
    .exec(deletarDocumento)

  val documentoIdFeeder = Iterator.continually(Map(
    "documentoId" -> (scala.util.Random.nextInt(100) + 1)
  ))

  val cenarioLeitura = scenario("Cenario Leitura Documentos")
    .exec(
      http("Listar Documentos")
        .get("/documentos")
        .check(status.is(200))
    )
    .pause(500.milliseconds)
    .feed(documentoIdFeeder)
    .exec(
      http("Buscar Documento Aleatorio")
        .get("/documentos/${documentoId}")
        .check(status.in(200, 404))
    )

  setUp(
    cenarioCompleto.inject(
      rampUsers(10).during(30.seconds),
      constantUsersPerSec(2).during(60.seconds),
      rampUsersPerSec(2).to(5).during(30.seconds)
    ),
    cenarioLeitura.inject(
      constantUsersPerSec(5).during(120.seconds)
    )
  ).protocols(httpProtocol)
    .assertions(
      global.successfulRequests.percent.gte(95),
      global.responseTime.max.lte(2000),
      global.responseTime.mean.lte(500)
    )

}

