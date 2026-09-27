package com.urent.feature.app.ui.routing

import com.urent.core.ui.routing.Event
import com.urent.feature.app.ui.screen.home.Tab

sealed interface FlowEvent : Event {
  data class TabChangeRequested(val tab: Tab) : FlowEvent
}
