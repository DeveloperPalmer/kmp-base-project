package com.kmpbaseproject.core.component

import android.content.Context
import androidx.work.WorkerFactory
import com.kmpbaseproject.core.domain.ApplicationContext
import com.kmpbaseproject.core.domain.configuration.BuildConfiguration
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
  abstract val workerFactory: WorkerFactory

  abstract fun foregroundComponentFactory(): AndroidForegroundComponent.Factory
}
