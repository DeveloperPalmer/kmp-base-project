package com.kmpbaseproject.feature.hometabs.ui.screen.home

import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.feature.hometabs.domain.di.HomeTabsScope
import com.kmpbaseproject.feature.hometabs.ui.entity.Tab
import com.kmpbaseproject.feature.hometabs.ui.routing.FlowEvent
import com.kmpbaseproject.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer

@Inject
@ViewModel(HomeTabsScope::class)
class HomeViewModel : BaseViewModel<ViewState, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState())

  fun navigateBack() {
    selectTab(Tab.Cities)
  }

  fun selectTab(tab: Tab) = intent {
    reduce { state.copy(selectedTab = tab) }
    sendEvent(FlowEvent.TabChangeRequested(tab))
  }
}
