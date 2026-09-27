package com.urent.feature.citydetails.ui.screen.cityDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.urent.core.ui.mvi.MviScreen
import com.urent.resources.Res
import com.urent.resources.city_details_screen_title
import com.urent.uikit.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun CityDetailsScreen(viewModel: CityDetailsViewModel) {
  return MviScreen(viewModel) { state, _ ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background.primary),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = stringResource(Res.string.city_details_screen_title),
        style = AppTheme.typography.title3,
      )
      Text(
        text = state.cityId.toString(),
        style = AppTheme.typography.body1,
      )
    }
  }
}
