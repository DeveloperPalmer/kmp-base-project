package com.kmpbaseproject.feature.app.ui.screen.home

import com.kmpbaseproject.core.domain.di.AppFlowScope
import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(AppFlowScope::class)
class HomeViewModel : BaseViewModel<ViewState, Nothing, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState)

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: Nothing) = Unit
}
