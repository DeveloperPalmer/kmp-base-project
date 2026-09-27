package com.kmpbaseproject.feature.app.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kmpbaseproject.resources.Res
import com.kmpbaseproject.resources.app_name
import com.kmpbaseproject.uikit.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen() {
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
