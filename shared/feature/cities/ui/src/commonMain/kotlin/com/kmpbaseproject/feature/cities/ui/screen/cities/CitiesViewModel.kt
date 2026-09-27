package com.kmpbaseproject.feature.cities.ui.screen.cities

import com.kmpbaseproject.core.domain.asLceState
import com.kmpbaseproject.core.domain.startOnSubscribe
import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.core.ui.toUiLceState
import com.kmpbaseproject.feature.cities.domain.CitiesModel
import com.kmpbaseproject.feature.cities.domain.di.CitiesScope
import com.kmpbaseproject.feature.cities.ui.routing.FlowEvent
import com.kmpbaseproject.lib.annotation.ViewModel
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(CitiesScope::class)
class CitiesViewModel(
  citiesModel: CitiesModel,
) : BaseViewModel<ViewState, ViewIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState()) {
    citiesModel.fetchCities.startOnSubscribe(
      argument1 = "",
      argument2 = FIRST_PAGE,
      argument3 = PAGE_LIMIT
    )
    citiesModel.fetchCities.jobFlow
      .asLceState()
      .map { it.toUiLceState() }
      .collect { contentLoadState -> reduce { state.copy(contentLoadState = contentLoadState) } }
  }

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: ViewIntent) {
    when (viewIntent) {
      is ViewIntent.OpenDetails -> sendEvent(FlowEvent.CityDetailsRequested(viewIntent.cityId))
    }
  }
}

private const val FIRST_PAGE = 1
private const val PAGE_LIMIT = 20
