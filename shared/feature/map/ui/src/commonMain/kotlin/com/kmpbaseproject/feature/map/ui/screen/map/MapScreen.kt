package com.kmpbaseproject.feature.map.ui.screen.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kmpbaseproject.core.ui.mvi.MviScreen
import com.kmpbaseproject.resources.Res
import com.kmpbaseproject.resources.map_screen_title
import com.kmpbaseproject.uikit.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun MapScreen(viewModel: MapViewModel) {
  return MviScreen(viewModel) { _, _ ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background.primary),
      contentAlignment = Alignment.Center,
    ) {
      Text(
        text = stringResource(Res.string.map_screen_title),
        style = AppTheme.typography.title3,
      )
    }
  }
}
