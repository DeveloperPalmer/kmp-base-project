package com.urent.feature.cities.ui.screen.cities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.urent.core.ui.entity.ContentLoadState
import com.urent.core.ui.entity.UiError
import com.urent.core.ui.mvi.MviScreen
import com.urent.core.ui.toUiLceState
import com.urent.feature.cities.domain.entity.City
import com.urent.resources.Res
import com.urent.resources.cities_list_title
import com.urent.resources.cities_search_placeholder
import com.urent.resources.ic_pin_24
import com.urent.resources.ic_search_24
import com.urent.resources.retry
import com.urent.uikit.component.button.PrimaryButton
import com.urent.uikit.component.message.ErrorMessage
import com.urent.uikit.component.shimmer.ShimmerSpacer
import com.urent.uikit.component.textfield.PrimaryTextField
import com.urent.uikit.theme.AppTheme
import com.urent.uikit.theme.VSpacer
import com.urent.uikit.theme.WSpacer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CitiesScreen(viewModel: CitiesViewModel) {
  MviScreen(viewModel) { state, onIntent ->
    val cities = state.cities.collectAsLazyPagingItems()
    when (val contentLoadState = cities.loadState.refresh.toUiLceState()) {
      is ContentLoadState.Error -> {
        CitiesError(
          error = contentLoadState.error,
          onRetry = cities::retry
        )
      }
      is ContentLoadState.Loading,
      is ContentLoadState.Ready -> {
        CitiesReady(
          query = state.citiesSearchQuery,
          cities = cities,
          isLoading = contentLoadState == ContentLoadState.Loading,
          onIntent = onIntent
        )
      }
    }
  }
}

@Composable
private fun CitiesReady(
  query: String,
  cities: LazyPagingItems<City>,
  isLoading: Boolean,
  onIntent: (ViewIntent) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(AppTheme.colors.background.primary)
  ) {
    CitiesTopBar()
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
      VSpacer(8.dp)
      PrimaryTextField(
        modifier = Modifier.fillMaxWidth(),
        value = query,
        onValueChange = { onIntent(ViewIntent.QueryChanged(it)) },
        placeholder = stringResource(Res.string.cities_search_placeholder),
        trailingIcon = Res.drawable.ic_search_24,
        trailingIconDescription = "search icon"
      )
      VSpacer(8.dp)
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .imePadding()
      ) {
        if (isLoading) {
          itemsIndexed(SKELETON_WIDTHS) { index, width ->
            Skeleton(
              width = width,
              showDivider = index > 0
            )
          }
        } else {
          items(
            count = cities.itemCount,
            key = cities.itemKey { it.id }
          ) { index ->
            val city = cities[index]
            if (city != null) {
              CityItem(
                city = city,
                showDivider = index > 0,
                onClick = { onIntent(ViewIntent.OpenDetails(city.id)) }
              )
            }
          }
          if (cities.loadState.append is LoadState.Loading) {
            item {
              Skeleton(
                width = SKELETON_WIDTHS.first(),
                showDivider = cities.itemCount > 0
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun CitiesTopBar() {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .statusBarsPadding()
      .height(52.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = stringResource(Res.string.cities_list_title),
      style = AppTheme.typography.title4,
      color = AppTheme.colors.text.primary
    )
  }
}

@Composable
private fun CityItem(
  city: City,
  showDivider: Boolean,
  onClick: () -> Unit,
) {
  City(
    modifier = Modifier.clickable(onClick = onClick),
    showDivider = showDivider
  ) {
    Text(
      modifier = Modifier.padding(vertical = 4.dp),
      text = "${city.name}, ${city.country}",
      style = AppTheme.typography.body1,
      color = AppTheme.colors.text.primary,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
  }
}

@Composable
private fun Skeleton(
  width: Dp,
  showDivider: Boolean,
) {
  City(showDivider = showDivider) {
    ShimmerSpacer(
      modifier = Modifier.padding(vertical = 4.dp),
      width = width,
      height = 22.dp
    )
  }
}

@Composable
private fun City(
  showDivider: Boolean,
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit,
) {
  Column(modifier = modifier) {
    if (showDivider) {
      HorizontalDivider(
        thickness = 1.dp,
        color = AppTheme.colors.divider.primary
      )
    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Icon(
        painter = painterResource(Res.drawable.ic_pin_24),
        tint = AppTheme.colors.icon.secondary,
        contentDescription = "pin icon"
      )
      content()
    }
  }
}

@Composable
private fun CitiesError(
  error: UiError,
  onRetry: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(AppTheme.colors.background.primary)
      .statusBarsPadding()
      .padding(horizontal = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    WSpacer()
    ErrorMessage(message = error.message)
    WSpacer()
    VSpacer(16.dp)
    PrimaryButton(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(Res.string.retry),
      onClick = onRetry
    )
    VSpacer(16.dp)
  }
}

private val SKELETON_WIDTHS = listOf(144.dp, 180.dp, 144.dp, 180.dp, 144.dp)
