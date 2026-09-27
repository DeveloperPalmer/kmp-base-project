package com.urent.feature.cities.ui.screen.cities

import com.urent.core.ui.mvi.BaseViewModel
import com.urent.feature.cities.domain.CitiesModel
import com.urent.feature.cities.domain.di.CitiesScope
import com.urent.feature.cities.ui.routing.FlowEvent
import com.urent.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(CitiesScope::class)
class CitiesViewModel(
  citiesModel: CitiesModel,
) : BaseViewModel<ViewState, ViewIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState) {
    citiesModel.fetchCities.start(
      argument1 = "",
      argument2 = FIRST_PAGE,
      argument3 = PAGE_LIMIT
    )
  }

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: ViewIntent) {
    when (viewIntent) {
      is ViewIntent.OpenDetails -> sendEvent(FlowEvent.CityDetailsRequested(viewIntent.cityId))
    }
  }
}

private const val FIRST_PAGE = 1
private const val PAGE_LIMIT = 20
