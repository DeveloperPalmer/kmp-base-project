package com.urent.feature.map.ui.screen.map.screen.map

import androidx.compose.runtime.Immutable
import com.urent.feature.map.ui.entity.CityInfo
import com.urent.feature.map.ui.entity.MapPin

@Immutable
data class ViewState(
  val pins: List<MapPin> = emptyList(),
  val city: CityInfo? = null,
)
