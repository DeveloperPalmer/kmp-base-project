package com.kmpbaseproject.feature.hometabs.ui.screen.home

import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.feature.hometabs.domain.di.HomeTabsScope
import com.kmpbaseproject.feature.hometabs.ui.routing.FlowEvent
import com.kmpbaseproject.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(HomeTabsScope::class)
class HomeViewModel : BaseViewModel<ViewState, ViewIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState)

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: ViewIntent) {
    when (viewIntent) {
      is ViewIntent.SelectTab -> sendEvent(FlowEvent.TabChangeRequested(viewIntent.tab))
    }
  }
}
