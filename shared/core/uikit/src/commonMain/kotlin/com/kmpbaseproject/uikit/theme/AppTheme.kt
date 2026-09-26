package com.kmpbaseproject.uikit.theme

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

  val typography: AppTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalAppTypography.current
}

@Composable
fun AppTheme(
  useDarkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = remember(useDarkTheme) { if (useDarkTheme) DarkAppColors else LightAppColors }
  val typography = remember { AppTypography() }

  CompositionLocalProvider(
    LocalAppColors provides colors,
    LocalAppTypography provides typography,
    LocalContentColor provides colors.text.primary,
    content = content
  )
}

private val LocalAppColors = compositionLocalOf<AppColors> {
  error("No AppColors provided")
}
