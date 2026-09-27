package com.urent.feature.cities.ui.screen.cities

import com.urent.core.ui.mvi.BaseViewModel
import com.urent.feature.cities.domain.di.CitiesScope
import com.urent.feature.cities.ui.routing.FlowEvent
import com.urent.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(CitiesScope::class)
class CitiesViewModel : BaseViewModel<ViewState, ViewIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState)

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: ViewIntent) {
    when (viewIntent) {
      is ViewIntent.OpenDetails -> sendEvent(FlowEvent.CityDetailsRequested)
    }
  }
}
