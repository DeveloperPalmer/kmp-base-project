package com.kmpbaseproject.feature.map.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kmpbaseproject.feature.map.domain.entity.MapViewport
import com.kmpbaseproject.feature.map.ui.entity.MapAction
import com.kmpbaseproject.feature.map.ui.entity.MapPin
import kotlinx.coroutines.flow.Flow

@Composable
internal expect fun Map(
  pins: List<MapPin>,
  actions: Flow<MapAction>,
  onCameraIdle: (MapViewport) -> Unit,
  onPinClick: (Long) -> Unit,
  modifier: Modifier = Modifier,
)
