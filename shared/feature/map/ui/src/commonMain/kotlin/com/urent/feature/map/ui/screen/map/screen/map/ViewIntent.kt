package com.urent.feature.map.ui.screen.map.screen.map

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ViewIntent {
  @Immutable
  data object ZoomIn : ViewIntent

  @Immutable
  data object ZoomOut : ViewIntent
}
