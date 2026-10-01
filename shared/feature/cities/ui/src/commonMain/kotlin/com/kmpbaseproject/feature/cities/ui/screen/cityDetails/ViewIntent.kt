package com.kmpbaseproject.feature.cities.ui.screen.cityDetails

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ViewIntent {
  @Immutable
  data object NavigateBack : ViewIntent

  @Immutable
  data object SearchCityInfo : ViewIntent
}
