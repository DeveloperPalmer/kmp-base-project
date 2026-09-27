package com.kmpbaseproject.feature.citydetails.ui.routing

import com.kmpbaseproject.core.ui.routing.Event

sealed interface FlowEvent : Event {
  data object CityDetailsDismissed : FlowEvent
  data class CitySearchRequested(val cityName: String) : FlowEvent
}
