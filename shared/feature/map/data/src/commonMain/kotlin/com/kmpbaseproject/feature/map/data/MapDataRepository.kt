@file:Suppress("UnusedImport")

package com.kmpbaseproject.feature.map.data

import com.kmpbaseproject.core.data.cities.CitiesDatabase
import com.kmpbaseproject.feature.map.data.entity.CitiesMapRequest
import com.kmpbaseproject.feature.map.data.entity.CitiesMapResponse
import com.kmpbaseproject.feature.map.domain.MapRepository
import com.kmpbaseproject.feature.map.domain.entity.GeoPoint
import com.kmpbaseproject.feature.map.domain.entity.MapCity
import com.kmpbaseproject.feature.map.domain.entity.MapViewport
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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
}

// The server rejects a radius that is not positive
private const val MIN_RADIUS_METERS = 1
