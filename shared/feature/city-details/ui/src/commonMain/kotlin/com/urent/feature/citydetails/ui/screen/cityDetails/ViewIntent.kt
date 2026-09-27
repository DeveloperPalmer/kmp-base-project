package com.urent.feature.citydetails.ui.screen.cityDetails

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ViewIntent {
  @Immutable
  data object NavigateBack : ViewIntent

  @Immutable
  data object SearchCityInfo : ViewIntent
}
