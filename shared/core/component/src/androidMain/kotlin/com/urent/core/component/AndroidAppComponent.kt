package com.urent.core.component

import android.content.Context
import com.urent.core.domain.ApplicationContext
import com.urent.core.domain.configuration.BuildConfiguration
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
abstract class AndroidAppComponent(
  @get:Provides
  @ApplicationContext
  val context: Context,
  @get:Provides
  val buildConfiguration: BuildConfiguration,
) : AppComponent {
  abstract fun foregroundComponentFactory(): AndroidForegroundComponent.Factory
}
