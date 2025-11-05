package com.matheus.playground.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class UsuarioSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
    .userAgentHeader("Gatling Load Test")

  val criarUsuarioFeeder = Iterator.continually(Map(
    "nome" -> s"Usuario_${System.currentTimeMillis()}",
    "email" -> s"usuario_${System.currentTimeMillis()}@example.com"
  ))

  val criarUsuario = exec(
    http("Criar Usuario")
      .post("/usuarios")
      .body(StringBody("""{"nome": "${nome}", "email": "${email}"}"""))
      .check(status.is(201))
      .check(jsonPath("$.id").saveAs("usuarioId"))
  )

  val listarUsuarios = exec(
    http("Listar Usuarios")
      .get("/usuarios")
      .check(status.is(200))
  )

  val buscarUsuarioPorId = exec(
    http("Buscar Usuario por ID")
      .get("/usuarios/${usuarioId}")
      .check(status.in(200, 404))
  )

  val atualizarUsuario = exec(
    http("Atualizar Usuario")
      .put("/usuarios/${usuarioId}")
      .body(StringBody("""{"nome": "Usuario Atualizado", "email": "atualizado@example.com"}"""))
      .check(status.in(200, 404))
  )

  val deletarUsuario = exec(
    http("Deletar Usuario")
      .delete("/usuarios/${usuarioId}")
      .check(status.in(204, 404))
  )

  val cenarioCompleto = scenario("Cenario Completo Usuarios")
    .feed(criarUsuarioFeeder)
    .exec(criarUsuario)
    .pause(1.second)
    .exec(listarUsuarios)
    .pause(1.second)
    .exec(buscarUsuarioPorId)
    .pause(1.second)
    .exec(atualizarUsuario)
    .pause(1.second)
    .exec(deletarUsuario)

  val usuarioIdFeeder = Iterator.continually(Map(
    "usuarioId" -> (scala.util.Random.nextInt(100) + 1)
  ))

  val cenarioLeitura = scenario("Cenario Leitura Usuarios")
    .exec(
      http("Listar Usuarios")
        .get("/usuarios")
        .check(status.is(200))
    )
    .pause(500.milliseconds)
    .feed(usuarioIdFeeder)
    .exec(
      http("Buscar Usuario Aleatorio")
        .get("/usuarios/${usuarioId}")
        .check(status.in(200, 404))
    )

  val cenarioCriacao = scenario("Cenario Criacao Usuarios")
    .feed(criarUsuarioFeeder)
    .exec(criarUsuario)
    .pause(1.second)

  setUp(
    cenarioCompleto.inject(
      rampUsers(5).during(20.seconds),
      constantUsersPerSec(1).during(40.seconds)
    ),
    cenarioLeitura.inject(
      constantUsersPerSec(3).during(60.seconds)
    ),
    cenarioCriacao.inject(
      rampUsersPerSec(1).to(3).during(30.seconds)
    )
  ).protocols(httpProtocol)
    .assertions(
      global.successfulRequests.percent.gte(95),
      global.responseTime.max.lte(1500),
      global.responseTime.mean.lte(400)
    )

}

