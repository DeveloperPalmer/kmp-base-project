package com.kmpbaseproject.feature.hometabs.ui.screen.home

import androidx.compose.runtime.Immutable
import com.kmpbaseproject.feature.hometabs.ui.entity.Tab

@Immutable
sealed interface ViewIntent {
  @Immutable
  data class SelectTab(val tab: Tab) : ViewIntent
}
