package com.kmpbaseproject.core.data.di

import com.kmpbaseproject.core.data.network.PlatformErrorConverter
import com.kmpbaseproject.core.data.network.installErrorResponseHandling
import com.kmpbaseproject.core.domain.configuration.BuildConfiguration
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface CoreDataModule {
  @Provides
  @SingleIn(AppScope::class)
  fun provideHttpClient(
    engine: HttpClientEngine,
    logger: Logger,
    platformErrorConverter: PlatformErrorConverter,
    buildConfiguration: BuildConfiguration,
  ): HttpClient {
    val json = Json { ignoreUnknownKeys = true }
    return HttpClient(engine) {
      install(ContentNegotiation) {
        json(json)
      }
      install(HttpTimeout) {
        connectTimeoutMillis = HTTP_TIMEOUT_MILLIS
        requestTimeoutMillis = HTTP_TIMEOUT_MILLIS
        socketTimeoutMillis = HTTP_TIMEOUT_MILLIS
      }
      install(Logging) {
        this.logger = logger
        level = buildConfiguration.httpLogLevel
      }
      installErrorResponseHandling(
        json = json,
        platformErrorConverter = platformErrorConverter
      )
    }
  }
}

private val BuildConfiguration.httpLogLevel: LogLevel
  get() = when (this) {
    is BuildConfiguration.Dev,
    is BuildConfiguration.Internal -> LogLevel.ALL
    is BuildConfiguration.Production -> LogLevel.NONE
  }

private const val HTTP_TIMEOUT_MILLIS = 60_000L
