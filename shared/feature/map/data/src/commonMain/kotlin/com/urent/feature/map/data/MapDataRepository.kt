@file:Suppress("UnusedImport")

package com.urent.feature.map.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneNotNull
import com.urent.core.data.cities.CitiesDatabase
import com.urent.feature.map.data.entity.CitiesMapRequest
import com.urent.feature.map.data.entity.CitiesMapResponse
import com.urent.feature.map.domain.MapRepository
import com.urent.feature.map.domain.entity.GeoPoint
import com.urent.feature.map.domain.entity.MapCity
import com.urent.feature.map.domain.entity.MapCityDetails
import com.urent.feature.map.domain.entity.MapViewport
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
import kotlin.math.roundToInt

@Inject
@ContributesBinding(AppScope::class)
class MapDataRepository(
  private val httpClient: HttpClient,
  private val citiesDatabase: CitiesDatabase,
) : MapRepository {
  override suspend fun cities(viewport: MapViewport): List<MapCity> {
    return withContext(Dispatchers.IO) {
      val request = CitiesMapRequest(
        centerLat = viewport.center.lat,
        centerLng = viewport.center.lon,
        radius = viewport.radiusMeters.roundToInt().coerceAtLeast(MIN_RADIUS_METERS),
      )
      val cities = httpClient.get(request).body<CitiesMapResponse>().items

      citiesDatabase.transaction {
        cities.forEach { city ->
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
      cities.map { city ->
        MapCity(
          id = city.id,
          name = city.name,
          location = GeoPoint(city.lat, city.lon),
        )
      }
    }
  }

  override fun cityDetails(id: Long): Flow<MapCityDetails> {
    return citiesDatabase.cityQueries
      .getCity(
        id = id,
        mapper = ::MapCityDetails
      )
      .asFlow()
      .mapToOneNotNull(Dispatchers.IO)
  }
}

// The server rejects a radius that is not positive
private const val MIN_RADIUS_METERS = 1
