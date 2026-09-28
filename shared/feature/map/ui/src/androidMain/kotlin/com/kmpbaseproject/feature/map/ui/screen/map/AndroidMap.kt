package com.kmpbaseproject.feature.map.ui.screen.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

@Composable
internal actual fun Map(modifier: Modifier) {
  val context = LocalContext.current
  val lifecycle = LocalLifecycleOwner.current.lifecycle
  val mapView = remember {
    MapView(context).apply {
      mapWindow.map.move(CameraPosition(DEFAULT_LOCATION, DEFAULT_ZOOM, 0f, 0f))
    }
  }

  // MapKit stops rendering unless both the factory and the view receive onStart/onStop.
  DisposableEffect(lifecycle, mapView) {
    val observer = LifecycleEventObserver { _, event ->
      when (event) {
        Lifecycle.Event.ON_START -> mapView.start()
        Lifecycle.Event.ON_STOP -> mapView.stop()
        else -> Unit
      }
    }
    lifecycle.addObserver(observer)
    onDispose {
      lifecycle.removeObserver(observer)
      if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
        mapView.stop()
      }
    }
  }

  AndroidView(
    modifier = modifier,
    factory = { mapView },
  )
}

private fun MapView.start() {
  MapKitFactory.getInstance().onStart()
  onStart()
}

private fun MapView.stop() {
  onStop()
  MapKitFactory.getInstance().onStop()
}

private const val DEFAULT_ZOOM = 11f
private val DEFAULT_LOCATION = Point(55.750447, 37.617495)
