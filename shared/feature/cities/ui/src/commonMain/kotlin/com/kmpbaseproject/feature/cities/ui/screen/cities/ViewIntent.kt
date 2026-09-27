package com.kmpbaseproject.feature.cities.ui.screen.cities

import androidx.compose.runtime.Immutable
import com.kmpbaseproject.core.ui.mvi.BlockingViewIntent

@Immutable
sealed interface ViewIntent {
  @Immutable
  data class QueryChanged(val query: String) : ViewIntent, BlockingViewIntent

  @Immutable
  data class OpenDetails(val cityId: Long) : ViewIntent
}
