package com.kmpbaseproject.feature.cities.ui.screen.cities

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ViewIntent {
  @Immutable
  data object OpenDetails : ViewIntent
}
