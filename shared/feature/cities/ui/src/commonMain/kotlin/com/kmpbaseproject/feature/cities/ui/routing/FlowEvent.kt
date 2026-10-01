package com.kmpbaseproject.feature.cities.ui.routing

import com.kmpbaseproject.core.ui.routing.Event

sealed interface FlowEvent : Event {
  data class CityDetailsRequested(val cityId: Long) : FlowEvent
  data object CityDetailsDismissed : FlowEvent

  data class CitySearchRequested(val cityName: String) : FlowEvent
}
