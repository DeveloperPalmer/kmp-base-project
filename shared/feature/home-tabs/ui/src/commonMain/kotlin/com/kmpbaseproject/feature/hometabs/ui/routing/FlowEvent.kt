package com.kmpbaseproject.feature.hometabs.ui.routing

import com.kmpbaseproject.core.ui.routing.Event
import com.kmpbaseproject.feature.hometabs.ui.entity.Tab

sealed interface FlowEvent : Event {
  data class TabChangeRequested(val tab: Tab) : FlowEvent
}
