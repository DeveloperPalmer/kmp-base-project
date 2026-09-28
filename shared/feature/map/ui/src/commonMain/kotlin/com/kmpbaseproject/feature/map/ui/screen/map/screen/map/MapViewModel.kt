package com.kmpbaseproject.feature.map.ui.screen.map.screen.map

import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.feature.map.domain.di.MapScope
import com.kmpbaseproject.feature.map.ui.adapter.MapAdapter
import com.kmpbaseproject.feature.map.ui.entity.MapAction
import com.kmpbaseproject.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(MapScope::class)
class MapViewModel(
  private val mapAdapter: MapAdapter,
) : BaseViewModel<ViewState, ViewIntent, MapAction>() {
  override val container = viewModelScope.orbitContainer(ViewState) {
    mapAdapter.actions.collect { action -> postSideEffect(action) }
  }

  override suspend fun Syntax<ViewState, MapAction>.handle(viewIntent: ViewIntent) {
    when (viewIntent) {
      is ViewIntent.ZoomIn -> {
        mapAdapter.accept(MapAction.ZoomIn)
      }
      is ViewIntent.ZoomOut -> {
        mapAdapter.accept(MapAction.ZoomOut)
      }
    }
  }
}
