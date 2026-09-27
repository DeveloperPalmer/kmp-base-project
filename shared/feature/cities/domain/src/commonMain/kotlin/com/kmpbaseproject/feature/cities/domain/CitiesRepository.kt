package com.kmpbaseproject.feature.cities.domain

import com.kmpbaseproject.feature.cities.domain.entity.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
  val cities: Flow<List<City>>

  suspend fun fetchCities(query: String, page: Int, limit: Int)
}
