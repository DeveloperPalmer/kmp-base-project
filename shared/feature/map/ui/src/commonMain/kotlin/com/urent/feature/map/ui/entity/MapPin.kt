package com.urent.feature.map.ui.entity

import androidx.compose.runtime.Immutable
import com.urent.feature.map.domain.entity.GeoPoint

@Immutable
data class MapPin(
  val id: Long,
  val title: String,
  val location: GeoPoint,
)
