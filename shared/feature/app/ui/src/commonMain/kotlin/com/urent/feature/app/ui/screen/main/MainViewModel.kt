package com.urent.feature.app.ui.screen.main

import com.urent.core.ui.mvi.BaseViewModel
import org.orbitmvi.orbit.orbitContainer

class MainViewModel : BaseViewModel<ViewState, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState)
}
