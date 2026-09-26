package com.urent.core.data.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface CoreDataModule {
  @Provides
  @SingleIn(AppScope::class)
  fun provideHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
      install(ContentNegotiation) {
        json()
      }
    }
  }
}
