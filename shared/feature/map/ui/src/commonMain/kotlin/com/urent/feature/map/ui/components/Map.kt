package com.urent.feature.map.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.urent.feature.map.domain.entity.MapViewport
import com.urent.feature.map.ui.entity.MapAction
import com.urent.feature.map.ui.entity.MapPin
import kotlinx.coroutines.flow.Flow

@Composable
internal expect fun Map(
  pins: List<MapPin>,
  actions: Flow<MapAction>,
  onCameraIdle: (MapViewport) -> Unit,
  modifier: Modifier = Modifier,
)
