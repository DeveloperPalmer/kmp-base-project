package com.urent.feature.app.ui.screen.home

import com.urent.core.domain.di.HomeScope
import com.urent.core.ui.mvi.BaseViewModel
import com.urent.feature.app.ui.routing.FlowEvent
import com.urent.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(HomeScope::class)
class HomeViewModel : BaseViewModel<ViewState, ViewIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState)

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: ViewIntent) {
    when (viewIntent) {
      is ViewIntent.SelectTab -> sendEvent(FlowEvent.TabChangeRequested(viewIntent.tab))
    }
  }
}
