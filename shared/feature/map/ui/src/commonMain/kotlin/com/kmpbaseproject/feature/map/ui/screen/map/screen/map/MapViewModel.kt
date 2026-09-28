package com.kmpbaseproject.feature.map.ui.screen.map.screen.map

import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.feature.map.domain.MapModel
import com.kmpbaseproject.feature.map.domain.di.MapScope
import com.kmpbaseproject.feature.map.ui.adapter.MapAdapter
import com.kmpbaseproject.feature.map.ui.entity.MapAction
import com.kmpbaseproject.feature.map.ui.mapper.toMapPins
import com.kmpbaseproject.lib.annotation.ViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(MapScope::class)
class MapViewModel(
  private val mapAdapter: MapAdapter,
  private val mapModel: MapModel,
) : BaseViewModel<ViewState, ViewIntent, MapAction>() {
  override val container = viewModelScope.orbitContainer(ViewState(pins = emptyList())) {
    coroutineScope {
      launch {
        mapAdapter.actions.collect { action ->
          postSideEffect(action)
        }
      }
      launch {
        mapModel.cities.collect { cities ->
          reduce { state.copy(pins = cities.toMapPins(mapped = state.pins)) }
        }
      }
    }
  }

  override suspend fun Syntax<ViewState, MapAction>.handle(viewIntent: ViewIntent) {
    when (viewIntent) {
      is ViewIntent.ZoomIn -> {
        mapAdapter.accept(MapAction.ZoomIn)
      }
      is ViewIntent.ZoomOut -> {
        mapAdapter.accept(MapAction.ZoomOut)
      }
      is ViewIntent.CameraIdle -> {
        mapModel.changeViewport(viewIntent.viewport)
      }
    }
  }
}
