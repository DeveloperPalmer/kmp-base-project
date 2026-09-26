package com.kmpbaseproject.core.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
interface AndroidDataModule {
  @Provides
  fun provideHttpClientEngine(): HttpClientEngine = OkHttp.create()

  @Provides
  fun provideHttpLogger(): Logger = Logger.ANDROID
}
