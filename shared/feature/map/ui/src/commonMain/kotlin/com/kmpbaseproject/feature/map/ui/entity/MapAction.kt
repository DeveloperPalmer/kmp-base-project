package com.kmpbaseproject.feature.map.ui.entity

import androidx.compose.runtime.Immutable

@Immutable
sealed interface MapAction {
  @Immutable
  data object ZoomIn : MapAction

  @Immutable
  data object ZoomOut : MapAction
}
