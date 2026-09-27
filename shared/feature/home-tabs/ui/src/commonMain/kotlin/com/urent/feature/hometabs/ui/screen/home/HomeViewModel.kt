package com.urent.feature.hometabs.ui.screen.home

import com.urent.core.ui.mvi.BaseViewModel
import com.urent.feature.hometabs.domain.di.HomeTabsScope
import com.urent.feature.hometabs.ui.routing.FlowEvent
import com.urent.lib.annotation.ViewModel
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
