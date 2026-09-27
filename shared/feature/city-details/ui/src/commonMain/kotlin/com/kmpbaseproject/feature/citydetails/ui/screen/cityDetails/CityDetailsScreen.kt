package com.kmpbaseproject.feature.citydetails.ui.screen.cityDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
