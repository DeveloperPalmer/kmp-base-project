package com.urent.core.data.di

import com.urent.core.data.network.HttpClientProvider
import com.urent.core.data.network.PlatformErrorConverter
import com.urent.core.data.network.installErrorResponseHandling
import com.urent.core.domain.configuration.BuildConfiguration
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
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
    provider: HttpClientProvider,
    platformErrorConverter: PlatformErrorConverter
  ): HttpClient {
    val json = Json { ignoreUnknownKeys = true }
    return provider.create {
      install(ContentNegotiation) {
        json(json)
      }
      install(HttpTimeout) {
        connectTimeoutMillis = HTTP_TIMEOUT_MILLIS
        requestTimeoutMillis = HTTP_TIMEOUT_MILLIS
        socketTimeoutMillis = HTTP_TIMEOUT_MILLIS
      }
      installErrorResponseHandling(
        json = json,
        platformErrorConverter = platformErrorConverter
      )
    }
  }
}

internal val BuildConfiguration.httpLogLevel: LogLevel
  get() = when (this) {
    is BuildConfiguration.Dev,
    is BuildConfiguration.Internal -> LogLevel.ALL
    is BuildConfiguration.Production -> LogLevel.NONE
  }

private const val HTTP_TIMEOUT_MILLIS = 60_000L
