package com.urent.feature.app.routing

import com.urent.core.domain.di.AppFlowScope
import com.urent.core.domain.di.ForegroundScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@SingleIn(AppFlowScope::class)
@ContributesSubcomponent(AppFlowScope::class)
interface AppFlowComponent {
  @ContributesSubcomponent.Factory(ForegroundScope::class)
  interface Factory {
    fun create(): AppFlowComponent
  }
}
