package com.urent.feature.map.ui.mapper

import com.urent.feature.map.domain.entity.MapCity
import com.urent.feature.map.ui.entity.MapPin

internal fun MapCity.toMapPin(): MapPin {
  return MapPin(
    id = id,
    title = name,
    location = location,
  )
}
