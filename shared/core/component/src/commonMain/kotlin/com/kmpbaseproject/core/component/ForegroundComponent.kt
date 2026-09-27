package com.kmpbaseproject.core.component

import com.kmpbaseproject.feature.app.routing.AppFlowComponent

interface ForegroundComponent {
  fun appFlowComponentFactory(): AppFlowComponent.Factory
}
