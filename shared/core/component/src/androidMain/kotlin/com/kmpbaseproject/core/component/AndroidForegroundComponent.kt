package com.kmpbaseproject.core.component

import com.kmpbaseproject.core.domain.di.ForegroundScope
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@SingleIn(ForegroundScope::class)
@ContributesSubcomponent(ForegroundScope::class)
interface AndroidForegroundComponent : ForegroundComponent {
  @ContributesSubcomponent.Factory(AppScope::class)
  interface Factory {
    fun create(): AndroidForegroundComponent
  }
}
