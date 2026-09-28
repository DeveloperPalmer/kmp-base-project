package com.urent.feature.map.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.urent.feature.map.ui.entity.MapPin
import com.yandex.mapkit.mapview.MapView

@Composable
internal fun MapPinsEffect(mapView: MapView, pins: List<MapPin>) {
  val images = rememberPinImages()
  val layer = remember(mapView, images) { MapPinsLayer(mapView.mapWindow.map, images) }
  DisposableEffect(layer) {
    onDispose { layer.clear() }
  }
  LaunchedEffect(layer, pins) {
    layer.show(pins)
  }
}
