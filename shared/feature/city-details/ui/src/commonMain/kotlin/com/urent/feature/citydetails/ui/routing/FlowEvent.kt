package com.urent.feature.citydetails.ui.routing

import com.urent.core.ui.routing.Event

sealed interface FlowEvent : Event {
  data object CityDetailsDismissed : FlowEvent
  data class CitySearchRequested(val cityName: String) : FlowEvent
}
