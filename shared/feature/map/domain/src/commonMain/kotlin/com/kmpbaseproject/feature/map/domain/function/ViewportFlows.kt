package com.kmpbaseproject.feature.map.domain.function

import com.kmpbaseproject.feature.map.domain.entity.MapViewport
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.time.Duration.Companion.milliseconds

/** Areas worth a request: the camera has stayed still for a moment and moved to a different area. */
@OptIn(FlowPreview::class)
fun Flow<MapViewport>.settledAreas(): Flow<MapViewport> {
  return debounce(SETTLE_TIMEOUT)
    .distinctUntilChanged { old, new -> old.isSameArea(new) }
}

internal val SETTLE_TIMEOUT = 300.milliseconds
