package com.kmpbaseproject.feature.map.ui.mapper

import com.kmpbaseproject.feature.map.domain.entity.MapCityDetails
import com.kmpbaseproject.feature.map.ui.entity.CityInfo

internal fun MapCityDetails.toCityInfo(): CityInfo {
  return CityInfo(
    name = name,
    country = country,
    population = population,
  )
}
