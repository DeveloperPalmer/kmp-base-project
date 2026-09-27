package com.urent.feature.cities.ui.screen.cities

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ViewIntent {
  @Immutable
  data class OpenDetails(val cityId: Long) : ViewIntent
}
