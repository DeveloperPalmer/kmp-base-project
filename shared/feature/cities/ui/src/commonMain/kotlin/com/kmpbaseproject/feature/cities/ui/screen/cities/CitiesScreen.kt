package com.kmpbaseproject.feature.cities.ui.screen.cities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kmpbaseproject.core.ui.mvi.MviScreen
import com.kmpbaseproject.resources.Res
import com.kmpbaseproject.resources.cities_screen_title
import com.kmpbaseproject.resources.details
import com.kmpbaseproject.uikit.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun CitiesScreen(viewModel: CitiesViewModel) {
  return MviScreen(viewModel) { _, onIntent ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background.primary),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = stringResource(Res.string.cities_screen_title),
        style = AppTheme.typography.title3,
      )
      Button(onClick = { onIntent(ViewIntent.OpenDetails) }) {
        Text(text = stringResource(Res.string.details))
      }
    }
  }
}
