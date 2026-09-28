package com.urent.feature.map.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.urent.feature.map.ui.entity.MapAction
import kotlinx.coroutines.flow.Flow

@Composable
internal actual fun Map(
  actions: Flow<MapAction>,
  modifier: Modifier,
) = Unit
