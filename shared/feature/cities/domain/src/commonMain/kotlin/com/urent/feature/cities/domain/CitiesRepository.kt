package com.urent.feature.cities.domain

import androidx.paging.PagingSource
import com.urent.feature.cities.domain.entity.City

interface CitiesRepository {
  suspend fun fetchCities(query: String, page: Int, limit: Int)

  suspend fun nextPage(query: String): Int?

  fun cities(query: String): PagingSource<Int, City>
}
