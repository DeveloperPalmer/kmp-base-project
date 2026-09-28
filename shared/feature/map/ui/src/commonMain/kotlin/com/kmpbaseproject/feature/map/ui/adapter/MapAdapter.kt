package com.kmpbaseproject.feature.map.ui.adapter

import com.kmpbaseproject.feature.map.ui.entity.MapAction
import kotlinx.coroutines.flow.Flow

interface MapAdapter {
  val actions: Flow<MapAction>
  fun accept(action: MapAction)
}
