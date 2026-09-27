package com.urent.core.component

import com.urent.feature.app.routing.AppFlowComponent

interface ForegroundComponent {
  fun appFlowComponentFactory(): AppFlowComponent.Factory
}
