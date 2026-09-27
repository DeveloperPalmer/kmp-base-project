@file:Suppress("UnusedImport")

package com.kmpbaseproject.feature.cities.data

import androidx.paging.PagingSource
import app.cash.sqldelight.paging3.QueryPagingSource
import com.kmpbaseproject.core.data.cities.CitiesDatabase
import com.kmpbaseproject.feature.cities.data.entity.CitiesRequest
import com.kmpbaseproject.feature.cities.data.entity.CitiesResponse
import com.kmpbaseproject.feature.cities.domain.CitiesRepository
import com.kmpbaseproject.feature.cities.domain.entity.City
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(AppScope::class)
class CitiesDataRepository(
  private val httpClient: HttpClient,
  private val citiesDatabase: CitiesDatabase,
) : CitiesRepository {
  override suspend fun fetchCities(query: String, page: Int, limit: Int) {
    return withContext(Dispatchers.IO) {
      val request = CitiesRequest(
        query = query,
        page = page,
        limit = limit
      )
      val response = httpClient.get(request).body<CitiesResponse>()

      citiesDatabase.transaction {
        if (page == FIRST_PAGE) {
          citiesDatabase.citySearchResultQueries.deleteBySearchQuery(query)
        }
        response.items.forEachIndexed { index, city ->
          citiesDatabase.cityQueries.insert(
            id = city.id,
            name = city.name,
            country = city.country,
            lat = city.lat,
            lon = city.lon,
            pop = city.pop,
          )
          citiesDatabase.citySearchResultQueries.insert(
            cityId = city.id,
            searchQuery = query,
            position = positionInSearch(
              page = page,
              pageSize = limit,
              indexOnPage = index
            ),
          )
        }
        citiesDatabase.citySearchQueries.upsert(
          searchQuery = query,
          nextPage = nextPageOrNull(
            page = page,
            pageSize = limit,
            total = response.total
          ),
        )
      }
    }
  }

  override suspend fun nextPage(query: String): Int? {
    return withContext(Dispatchers.IO) {
      citiesDatabase.citySearchQueries
        .getNextPage(query)
        .executeAsOneOrNull()
        ?.nextPage
        ?.toInt()
    }
  }

  override fun cities(query: String): PagingSource<Int, City> {
    return QueryPagingSource(
      context = Dispatchers.IO,
      transacter = citiesDatabase.citySearchResultQueries,
      countQuery = citiesDatabase.citySearchResultQueries.countQuery(query),
      queryProvider = { limit, offset ->
        citiesDatabase.citySearchResultQueries.getCities(
          searchQuery = query,
          limit = limit,
          offset = offset,
          mapper = ::City
        )
      }
    )
  }
}

private fun positionInSearch(page: Int, pageSize: Int, indexOnPage: Int): Long {
  return ((page - FIRST_PAGE) * pageSize + indexOnPage).toLong()
}

private fun nextPageOrNull(page: Int, pageSize: Int, total: Int): Long? {
  val loadedCount = page * pageSize
  return if (loadedCount < total) (page + 1).toLong() else null
}

private const val FIRST_PAGE = 1
