package com.kmpbaseproject.feature.map.ui.screen.map

import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.feature.map.domain.di.MapScope
import com.kmpbaseproject.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(MapScope::class)
class MapViewModel : BaseViewModel<ViewState, ViewIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState)

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: ViewIntent) = Unit
}
