package com.urent.feature.cities.ui.routing

import com.urent.core.ui.routing.Event

sealed interface FlowEvent : Event {
  data class CityDetailsRequested(val cityId: Long) : FlowEvent
}
