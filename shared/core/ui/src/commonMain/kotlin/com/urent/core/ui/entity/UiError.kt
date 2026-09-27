package com.urent.core.ui.entity

import androidx.compose.runtime.Immutable

@Immutable
data class UiError(
  val cause: Throwable?,
  val message: UiMessage,
)
