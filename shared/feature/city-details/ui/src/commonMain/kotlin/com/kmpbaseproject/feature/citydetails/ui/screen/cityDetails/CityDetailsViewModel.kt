package com.kmpbaseproject.feature.citydetails.ui.screen.cityDetails

import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.feature.citydetails.domain.CityDetailsModel
import com.kmpbaseproject.feature.citydetails.domain.di.CityDetailsScope
import com.kmpbaseproject.feature.citydetails.ui.routing.FlowEvent
import com.kmpbaseproject.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(CityDetailsScope::class)
class CityDetailsViewModel(
  @Assisted
  cityId: Long,
  cityDetailsModel: CityDetailsModel,
) : BaseViewModel<ViewState, ViewIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState()) {
    cityDetailsModel.city(cityId)
      .collect { city -> reduce { state.copy(city = city) } }
  }

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: ViewIntent) {
    when (viewIntent) {
      is ViewIntent.NavigateBack -> {
        sendEvent(FlowEvent.CityDetailsDismissed)
      }
      is ViewIntent.SearchCityInfo -> {
        val city = state.city ?: return
        sendEvent(FlowEvent.CitySearchRequested(city.name))
      }
    }
  }
}
