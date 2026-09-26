package com.urent.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

object AppTheme {
  val colors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current
}

@Composable
fun AppTheme(
  useDarkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = remember(useDarkTheme) { if (useDarkTheme) DarkAppColors else LightAppColors }

  CompositionLocalProvider(
    LocalAppColors provides colors,
    LocalContentColor provides colors.text.primary,
    content = content
  )
}

private val LocalAppColors = compositionLocalOf<AppColors> {
  error("No AppColors provided")
}
