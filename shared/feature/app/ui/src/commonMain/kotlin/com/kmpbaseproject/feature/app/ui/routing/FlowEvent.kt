package com.kmpbaseproject.feature.app.ui.routing

import com.kmpbaseproject.core.ui.routing.Event
import com.kmpbaseproject.feature.app.ui.screen.home.Tab

sealed interface FlowEvent : Event {
  data class TabChangeRequested(val tab: Tab) : FlowEvent
}
