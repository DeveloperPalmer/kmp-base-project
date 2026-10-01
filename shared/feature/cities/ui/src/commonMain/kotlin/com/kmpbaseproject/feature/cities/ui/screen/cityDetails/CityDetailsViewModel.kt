package com.kmpbaseproject.feature.cities.ui.screen.cityDetails

import com.kmpbaseproject.core.domain.di.AppFlowScope
import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.feature.cities.domain.CitiesModel
import com.kmpbaseproject.feature.cities.ui.routing.FlowEvent
import com.kmpbaseproject.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer

@Inject
@ViewModel(AppFlowScope::class)
class CityDetailsViewModel(
  @Assisted
  cityId: Long,
  citiesModel: CitiesModel,
) : BaseViewModel<ViewState, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState()) {
    citiesModel.cityDetails(cityId)
      .collect { city -> reduce { state.copy(city = city) } }
  }

  fun navigateBack() = intent {
    sendEvent(FlowEvent.CityDetailsDismissed)
  }

  fun searchCityInfo() = intent {
    val city = state.city ?: return@intent
    sendEvent(FlowEvent.CitySearchRequested(city.name))
  }
}
