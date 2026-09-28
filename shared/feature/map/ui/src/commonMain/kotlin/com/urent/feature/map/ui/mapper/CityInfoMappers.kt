package com.urent.feature.map.ui.mapper

import com.urent.feature.map.domain.entity.MapCityDetails
import com.urent.feature.map.ui.entity.CityInfo

internal fun MapCityDetails.toCityInfo(): CityInfo {
  return CityInfo(
    id = id,
    name = name,
    country = country,
    population = population,
  )
}
