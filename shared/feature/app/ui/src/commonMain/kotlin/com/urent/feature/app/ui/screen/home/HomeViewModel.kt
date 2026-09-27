package com.urent.feature.app.ui.screen.home

import com.urent.core.domain.di.AppFlowScope
import com.urent.core.ui.mvi.BaseViewModel
import com.urent.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer

@Inject
@ViewModel(AppFlowScope::class)
class HomeViewModel : BaseViewModel<ViewState, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState)
}
