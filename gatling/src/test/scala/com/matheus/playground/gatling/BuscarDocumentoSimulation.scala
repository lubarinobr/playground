package com.matheus.playground.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BuscarDocumentoSimulation extends Simulation {

  val porta = Option(System.getProperty("porta")).getOrElse("8080")
  val baseUrl = s"http://localhost:${porta}"

  val httpProtocol = http
    .baseUrl(baseUrl)
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
    .userAgentHeader("Gatling Load Test - Buscar Documentos Usuario 6")

  val usuarioId = 6L

  val buscarDocumentosUsuario6 = exec(
    http("Buscar Documentos do Usuario 6")
      .get(s"/documentos/usuario/${usuarioId}")
      .check(status.is(200))
  )

  val cenarioBuscaIntensiva = scenario("Busca Intensiva - Usuario 6")
    .exec(buscarDocumentosUsuario6)
    .pause(100.milliseconds)

  val cenarioBuscaSustentada = scenario("Busca Sustentada - Usuario 6")
    .exec(buscarDocumentosUsuario6)
    .pause(500.milliseconds)

  val cenarioBuscaPico = scenario("Busca com Pico - Usuario 6")
    .exec(buscarDocumentosUsuario6)
    .pause(200.milliseconds)

  setUp(
    cenarioBuscaIntensiva.inject(
      nothingFor(5.seconds),
      rampUsers(100).during(30.seconds),
      constantUsersPerSec(10).during(60.seconds),
      rampUsersPerSec(10).to(20).during(30.seconds),
      constantUsersPerSec(20).during(60.seconds),
      rampUsersPerSec(20).to(10).during(30.seconds)
    ),
    cenarioBuscaSustentada.inject(
      nothingFor(10.seconds),
      constantUsersPerSec(5).during(120.seconds)
    ),
    cenarioBuscaPico.inject(
      nothingFor(15.seconds),
      rampUsers(200).during(10.seconds),
      nothingFor(5.seconds),
      rampUsers(200).during(10.seconds),
      nothingFor(5.seconds),
      rampUsers(200).during(10.seconds)
    )
  ).protocols(httpProtocol)
    .assertions(
      global.successfulRequests.percent.gte(95),
      global.responseTime.max.lte(1000),
      global.responseTime.mean.lte(300),
      global.responseTime.percentile3.lte(500),
      global.responseTime.percentile4.lte(800),
      forAll.successfulRequests.percent.gte(95),
      details("Buscar Documentos do Usuario 6").responseTime.mean.lte(300),
      details("Buscar Documentos do Usuario 6").responseTime.max.lte(1000)
    )

}

