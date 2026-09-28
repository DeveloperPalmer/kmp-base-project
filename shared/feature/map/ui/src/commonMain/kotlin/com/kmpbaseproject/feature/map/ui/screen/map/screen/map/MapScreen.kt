package com.kmpbaseproject.feature.map.ui.screen.map.screen.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.layoutId
import com.kmpbaseproject.core.ui.mvi.MviScreen
import com.kmpbaseproject.core.ui.safeDrawingHorizontal
import com.kmpbaseproject.feature.map.ui.components.CitySheetContent
import com.kmpbaseproject.feature.map.ui.components.Map
import com.kmpbaseproject.feature.map.ui.components.MapBottomSheet
import com.kmpbaseproject.feature.map.ui.components.MapControlsLayer
import com.kmpbaseproject.feature.map.ui.components.ZoomControl
import com.kmpbaseproject.feature.map.ui.components.rememberMapControlsState
import com.kmpbaseproject.feature.map.ui.components.rememberMapSheetState
import com.kmpbaseproject.feature.map.ui.entity.MapControl

@Composable
fun MapScreen(viewModel: MapViewModel) {
  return MviScreen(viewModel) { state, onIntent ->
    val sheetState = rememberMapSheetState()
    val controlsState = rememberMapControlsState(listOf(sheetState))
    Box(
      modifier = Modifier
        .fillMaxSize()
        .clipToBounds(),
    ) {
      Map(
        modifier = Modifier.fillMaxSize(),
        pins = state.pins,
        selectedPinId = state.city?.id,
        actions = sideEffects,
        onPinClick = { cityId -> onIntent(ViewIntent.SelectCity(cityId)) },
        onCameraIdle = { viewport -> onIntent(ViewIntent.CameraIdle(viewport)) },
      )
      MapControlsLayer(
        modifier = Modifier
          .fillMaxSize()
          .windowInsetsPadding(WindowInsets.safeDrawingHorizontal),
        state = controlsState,
      ) {
        ZoomControl(
          modifier = Modifier.layoutId(MapControl.Zoom),
          visible = controlsState.isVisible(MapControl.Zoom),
          onZoomInClick = { onIntent(ViewIntent.ZoomIn) },
          onZoomOutClick = { onIntent(ViewIntent.ZoomOut) },
        )
      }
      MapBottomSheet(
        state = sheetState,
        visible = state.city != null,
        onDismiss = { onIntent(ViewIntent.DismissCity) },
      ) {
        state.city?.let { city ->
          CitySheetContent(
            city = city,
            onSearchClick = { onIntent(ViewIntent.SearchCityInfo) },
          )
        }
      }
    }
  }
}
