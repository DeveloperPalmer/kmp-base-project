package com.kmpbaseproject.feature.cities.domain

import androidx.paging.PagingSource
import com.kmpbaseproject.feature.cities.domain.entity.City
import com.kmpbaseproject.feature.cities.domain.entity.CityDetails
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
  suspend fun fetchCities(query: String, page: Int, limit: Int)

  suspend fun nextPage(query: String): Int?

  fun citiesPagingSource(query: String): PagingSource<Int, City>
  suspend fun cities(query: String): List<City>

  fun cityDetails(id: Long): Flow<CityDetails>
}
