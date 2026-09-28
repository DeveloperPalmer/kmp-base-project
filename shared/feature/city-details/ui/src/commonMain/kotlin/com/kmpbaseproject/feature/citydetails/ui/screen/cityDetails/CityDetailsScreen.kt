package com.kmpbaseproject.feature.citydetails.ui.screen.cityDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kmpbaseproject.core.ui.formatPopulation
import com.kmpbaseproject.core.ui.mvi.MviScreen
import com.kmpbaseproject.feature.citydetails.domain.entity.CityDetails
import com.kmpbaseproject.resources.Res
import com.kmpbaseproject.resources.city_details_city
import com.kmpbaseproject.resources.city_details_country
import com.kmpbaseproject.resources.city_details_population
import com.kmpbaseproject.resources.city_details_population_value
import com.kmpbaseproject.resources.city_details_search
import com.kmpbaseproject.resources.ic_arrow_back_24
import com.kmpbaseproject.uikit.component.button.PrimaryButton
import com.kmpbaseproject.uikit.component.info.InfoItem
import com.kmpbaseproject.uikit.theme.AppTheme
import com.kmpbaseproject.uikit.theme.VSpacer
import com.kmpbaseproject.uikit.theme.WSpacer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CityDetailsScreen(viewModel: CityDetailsViewModel) {
  MviScreen(viewModel) { state, onIntent ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background.primary)
        .statusBarsPadding()
        .navigationBarsPadding()
    ) {
      CityDetailsTopBar(
        onBack = { onIntent(ViewIntent.NavigateBack) }
      )
      val city = state.city
      if (city != null) {
        CityInfo(city = city)
      }
      WSpacer()
      VSpacer(16.dp)
      PrimaryButton(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        text = stringResource(Res.string.city_details_search),
        onClick = { onIntent(ViewIntent.SearchCityInfo) }
      )
      VSpacer(16.dp)
    }
  }
}

@Composable
private fun CityDetailsTopBar(onBack: () -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(52.dp)
      .padding(horizontal = 4.dp),
    contentAlignment = Alignment.CenterStart
  ) {
    IconButton(onClick = onBack) {
      Icon(
        painter = painterResource(Res.drawable.ic_arrow_back_24),
        contentDescription = "arrow back icon",
        tint = AppTheme.colors.icon.primary
      )
    }
  }
}

@Composable
private fun CityInfo(city: CityDetails) {
  Column(modifier = Modifier.padding(horizontal = 16.dp)) {
    VSpacer(8.dp)
    InfoItem(
      label = stringResource(Res.string.city_details_city),
      value = city.name
    )
    InfoItem(
      label = stringResource(Res.string.city_details_country),
      value = city.country
    )
    InfoItem(
      label = stringResource(Res.string.city_details_population),
      value = stringResource(Res.string.city_details_population_value, formatPopulation(city.population))
    )
  }
}
