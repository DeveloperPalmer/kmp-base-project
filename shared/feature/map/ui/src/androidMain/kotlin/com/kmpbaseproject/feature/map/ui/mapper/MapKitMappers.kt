package com.kmpbaseproject.feature.map.ui.mapper

import com.kmpbaseproject.feature.map.domain.entity.GeoPoint
import com.yandex.mapkit.geometry.Point

internal fun GeoPoint.toPoint(): Point {
  return Point(lat, lon)
}

internal fun Point.toGeoPoint(): GeoPoint {
  return GeoPoint(latitude, longitude)
}
