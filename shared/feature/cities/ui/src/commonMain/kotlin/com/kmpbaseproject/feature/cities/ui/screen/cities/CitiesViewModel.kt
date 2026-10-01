package com.kmpbaseproject.feature.cities.ui.screen.cities

import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.feature.cities.domain.CitiesModel
import com.kmpbaseproject.feature.cities.domain.di.CitiesScope
import com.kmpbaseproject.feature.cities.ui.routing.FlowEvent
import com.kmpbaseproject.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(CitiesScope::class)
class CitiesViewModel(
  private val citiesModel: CitiesModel,
) : BaseViewModel<ViewState, ViewIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(
    ViewState(cities = citiesModel.cities)
  )

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: ViewIntent) {
    when (viewIntent) {
      is ViewIntent.QueryChanged -> {
        reduce { state.copy(citiesSearchQuery = viewIntent.query) }
        citiesModel.search(viewIntent.query)
      }
      is ViewIntent.OpenDetails -> {
        sendEvent(FlowEvent.CityDetailsRequested(viewIntent.cityId))
      }
    }
  }
}
