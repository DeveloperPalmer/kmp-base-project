package com.urent.feature.app.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.urent.resources.Res
import com.urent.resources.app_name
import com.urent.uikit.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen() {
  AppTheme {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background.primary),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = stringResource(Res.string.app_name),
        style = AppTheme.typography.title3
      )
    }
  }
}
