package com.kmpbaseproject.feature.map.ui.routing

import com.kmpbaseproject.core.ui.routing.Event

sealed interface FlowEvent : Event {
  data class CitySearchRequested(val cityName: String) : FlowEvent
}
