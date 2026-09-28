package com.kmpbaseproject.feature.map.domain

import com.kmpbaseproject.feature.map.domain.entity.MapCity
import com.kmpbaseproject.feature.map.domain.entity.MapCityDetails
import com.kmpbaseproject.feature.map.domain.entity.MapViewport
import kotlinx.coroutines.flow.Flow

interface MapRepository {
  suspend fun cities(viewport: MapViewport): List<MapCity>
  fun cityDetails(id: Long): Flow<MapCityDetails>
}
