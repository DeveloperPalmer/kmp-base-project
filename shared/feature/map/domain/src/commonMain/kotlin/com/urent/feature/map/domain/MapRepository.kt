package com.urent.feature.map.domain

import com.urent.feature.map.domain.entity.MapCity
import com.urent.feature.map.domain.entity.MapViewport

interface MapRepository {
  suspend fun cities(viewport: MapViewport): List<MapCity>
}
