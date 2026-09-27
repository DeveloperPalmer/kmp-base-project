package com.urent.feature.cities.ui.screen.cities

import androidx.compose.runtime.Immutable
import com.urent.core.ui.entity.ContentLoadState

@Immutable
data class ViewState(
  val contentLoadState: ContentLoadState = ContentLoadState.NotStarted,
)
