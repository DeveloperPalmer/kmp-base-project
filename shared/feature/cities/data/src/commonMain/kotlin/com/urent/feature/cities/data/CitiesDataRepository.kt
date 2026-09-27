@file:Suppress("UnusedImport")

package com.urent.feature.cities.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.urent.core.data.cities.CitiesDatabase
import com.urent.feature.cities.data.entity.CitiesRequest
import com.urent.feature.cities.data.entity.CitiesResponse
import com.urent.feature.cities.domain.CitiesRepository
import com.urent.feature.cities.domain.entity.City
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
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
        response.items.forEach { city ->
          citiesDatabase.cityQueries.insert(
            id = city.id,
            name = city.name,
            country = city.country,
            lat = city.lat,
            lon = city.lon,
            pop = city.pop,
          )
        }
      }
    }
  }

  override val cities: Flow<List<City>> = citiesDatabase.cityQueries
    .getCities(::City)
    .asFlow()
    .mapToList(Dispatchers.IO)
}
