package com.kmpbaseproject.core.component

import com.kmpbaseproject.core.domain.configuration.BuildConfiguration
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
abstract class AndroidAppComponent(
  @get:Provides
  val buildConfiguration: BuildConfiguration,
) : AppComponent
