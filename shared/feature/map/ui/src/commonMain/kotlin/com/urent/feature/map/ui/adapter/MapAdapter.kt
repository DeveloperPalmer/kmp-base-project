package com.urent.feature.map.ui.adapter

import com.urent.feature.map.ui.entity.MapAction
import kotlinx.coroutines.flow.Flow

interface MapAdapter {
  val actions: Flow<MapAction>
  fun accept(action: MapAction)
}
