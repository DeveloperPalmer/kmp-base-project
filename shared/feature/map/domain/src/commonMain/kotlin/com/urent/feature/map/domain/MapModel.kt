package com.urent.feature.map.domain

import com.urent.core.domain.ReactiveModel
import com.urent.feature.map.domain.di.MapScope
import com.urent.feature.map.domain.entity.MapCity
import com.urent.feature.map.domain.entity.MapViewport
import com.urent.feature.map.domain.mapper.accumulated
import com.urent.feature.map.domain.mapper.settledAreas
import com.urent.lib.annotation.FlowCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(MapScope::class)
class MapModel(
  @FlowCoroutineScope(MapScope::class)
  coroutineScope: CoroutineScope,
  mapRepository: MapRepository,
) : ReactiveModel(coroutineScope) {
  private val viewports = MutableStateFlow<MapViewport?>(null)

  val cities: StateFlow<List<MapCity>> = viewports
    .filterNotNull()
    .settledAreas()
    .flatMapLatest { viewport ->
      flow { emit(mapRepository.cities(viewport)) }
        // The last cities stay on the map; a failed area is requested again once the camera moves away.
        .catch { error -> error.printStackTrace() }
    }
    .accumulated()
    .stateIn(
      scope = scope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList()
    )

  fun changeViewport(viewport: MapViewport) {
    viewports.value = viewport
  }
}
