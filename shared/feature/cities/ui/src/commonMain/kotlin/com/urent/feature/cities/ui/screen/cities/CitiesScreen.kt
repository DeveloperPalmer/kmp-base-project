package com.urent.feature.cities.ui.screen.cities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.urent.core.ui.mvi.MviScreen
import com.urent.resources.Res
import com.urent.resources.cities_screen_title
import com.urent.resources.details
import com.urent.uikit.theme.AppTheme
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
      Button(onClick = { onIntent(ViewIntent.OpenDetails(cityId = TEST_CITY_ID)) }) {
        Text(text = stringResource(Res.string.details))
      }
    }
  }
}

// Until the cities list is loaded, Details opens a test city
private const val TEST_CITY_ID = 1L
