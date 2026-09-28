package com.kmpbaseproject.feature.map.ui.mapper

import com.kmpbaseproject.feature.map.domain.entity.MapCity
import com.kmpbaseproject.feature.map.ui.entity.MapPin

internal fun MapCity.toMapPin(): MapPin {
  return MapPin(
    id = id,
    title = name,
    location = location,
  )
}
