package com.urent.feature.app.ui.screen.main

import com.urent.core.domain.di.AppFlowScope
import com.urent.core.ui.mvi.BaseViewModel
import com.urent.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer

@Inject
@ViewModel(AppFlowScope::class)
class MainViewModel : BaseViewModel<ViewState, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState)
}
