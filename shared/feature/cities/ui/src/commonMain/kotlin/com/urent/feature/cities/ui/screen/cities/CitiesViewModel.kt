package com.urent.feature.cities.ui.screen.cities

import com.urent.core.ui.mvi.BaseViewModel
import com.urent.feature.cities.domain.CitiesModel
import com.urent.feature.cities.domain.di.CitiesScope
import com.urent.feature.cities.ui.routing.FlowEvent
import com.urent.lib.annotation.ViewModel
import kotlinx.coroutines.flow.debounce
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax
import kotlin.time.Duration.Companion.milliseconds

@Inject
@ViewModel(CitiesScope::class)
class CitiesViewModel(citiesModel: CitiesModel) : BaseViewModel<ViewState, ViewIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(
    ViewState(cities = citiesModel.cities)
  ) {
    intents<ViewIntent.QueryChanged>()
      .debounce(300.milliseconds)
      .collect { intent -> citiesModel.search(intent.query) }
  }

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: ViewIntent) {
    when (viewIntent) {
      is ViewIntent.QueryChanged -> {
        reduce { state.copy(citiesSearchQuery = viewIntent.query) }
      }
      is ViewIntent.OpenDetails -> {
        sendEvent(FlowEvent.CityDetailsRequested(viewIntent.cityId))
      }
    }
  }
}
