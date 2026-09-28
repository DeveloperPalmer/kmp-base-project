package com.kmpbaseproject.feature.map.domain

import com.kmpbaseproject.feature.map.domain.entity.MapCity
import com.kmpbaseproject.feature.map.domain.entity.MapViewport

interface MapRepository {
  suspend fun cities(viewport: MapViewport): List<MapCity>
}
