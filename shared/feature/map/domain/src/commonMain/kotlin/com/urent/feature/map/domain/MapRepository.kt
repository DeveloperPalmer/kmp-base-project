package com.urent.feature.map.domain

import com.urent.feature.map.domain.entity.MapCity
import com.urent.feature.map.domain.entity.MapCityDetails
import com.urent.feature.map.domain.entity.MapViewport
import kotlinx.coroutines.flow.Flow

interface MapRepository {
  suspend fun cities(viewport: MapViewport): List<MapCity>
  fun cityDetails(id: Long): Flow<MapCityDetails>
}
