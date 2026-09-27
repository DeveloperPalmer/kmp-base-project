package com.kmpbaseproject.feature.cities.ui.screen.cities

import androidx.compose.runtime.Immutable
import com.kmpbaseproject.core.ui.entity.ContentLoadState

@Immutable
data class ViewState(
  val contentLoadState: ContentLoadState = ContentLoadState.NotStarted,
)
