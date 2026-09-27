package com.kmpbaseproject.feature.cities.ui.screen.cityDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kmpbaseproject.core.ui.mvi.MviScreen
import com.kmpbaseproject.resources.Res
import com.kmpbaseproject.resources.city_details_screen_title
import com.kmpbaseproject.uikit.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun CityDetailsScreen(viewModel: CityDetailsViewModel) {
  return MviScreen(viewModel) { _, _ ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background.primary),
      contentAlignment = Alignment.Center,
    ) {
      Text(
        text = stringResource(Res.string.city_details_screen_title),
        style = AppTheme.typography.title3,
      )
    }
  }
}
