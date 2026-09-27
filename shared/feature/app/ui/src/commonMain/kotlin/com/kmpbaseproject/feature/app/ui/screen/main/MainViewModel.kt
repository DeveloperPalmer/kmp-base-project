package com.kmpbaseproject.feature.app.ui.screen.main

import com.kmpbaseproject.core.domain.di.AppFlowScope
import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer

@Inject
@ViewModel(AppFlowScope::class)
class MainViewModel : BaseViewModel<ViewState, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState)
}
