package com.urent.feature.hometabs.ui.routing

import com.urent.core.ui.routing.Event
import com.urent.feature.hometabs.ui.entity.Tab

sealed interface FlowEvent : Event {
  data class TabChangeRequested(val tab: Tab) : FlowEvent
}
