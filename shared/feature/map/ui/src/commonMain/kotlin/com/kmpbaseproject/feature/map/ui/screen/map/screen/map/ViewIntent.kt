package com.kmpbaseproject.feature.map.ui.screen.map.screen.map

import androidx.compose.runtime.Immutable
import com.kmpbaseproject.feature.map.domain.entity.MapViewport

@Immutable
sealed interface ViewIntent {
  @Immutable
  data object ZoomIn : ViewIntent

  @Immutable
  data object ZoomOut : ViewIntent

  @Immutable
  data class CameraIdle(val viewport: MapViewport) : ViewIntent

  @Immutable
  data class SelectCity(val cityId: Long) : ViewIntent

  @Immutable
  data object DismissCity : ViewIntent

  @Immutable
  data object SearchCityInfo : ViewIntent
}
