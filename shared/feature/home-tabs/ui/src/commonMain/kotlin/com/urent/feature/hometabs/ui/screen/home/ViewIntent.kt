package com.urent.feature.hometabs.ui.screen.home

import androidx.compose.runtime.Immutable
import com.urent.feature.hometabs.ui.entity.Tab

@Immutable
sealed interface ViewIntent {
  @Immutable
  data class SelectTab(val tab: Tab) : ViewIntent
}
