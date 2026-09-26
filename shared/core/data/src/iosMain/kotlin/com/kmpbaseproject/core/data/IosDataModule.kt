package com.kmpbaseproject.core.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
interface IosDataModule {
  @Provides
  fun provideHttpClientEngine(): HttpClientEngine = Darwin.create()

  @Provides
  fun provideHttpLogger(): Logger = Logger.DEFAULT
}
