package com.urent.feature.map.ui.routing

import com.urent.core.ui.routing.Event

sealed interface FlowEvent : Event {
  data class CitySearchRequested(val cityName: String) : FlowEvent
}
