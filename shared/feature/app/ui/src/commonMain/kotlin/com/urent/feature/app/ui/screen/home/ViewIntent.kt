package com.urent.feature.app.ui.screen.home

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ViewIntent {
  @Immutable
  data class SelectTab(val tab: Tab) : ViewIntent
}
