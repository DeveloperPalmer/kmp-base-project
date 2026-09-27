package com.urent.feature.home.routing

import androidx.compose.runtime.Stable
import com.urent.core.routing.di.FlowComponent
import com.urent.core.ui.viewmodel.AssistedViewModelProvider
import com.urent.core.ui.viewmodel.ViewModelProvider
import com.urent.core.ui.viewmodel.emptyAssistedViewModelProvider
import com.urent.core.ui.viewmodel.emptyViewModelProvider
import com.urent.feature.citydetails.routing.CityDetailsFlowComponent
import com.urent.feature.home.domain.di.HomeScope
import com.urent.feature.hometabs.routing.HomeTabsFlowComponent
import com.urent.lib.annotation.MergeSubcomponent
import com.urent.lib.annotation.ScopedViewModel
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

@Stable
@MergeSubcomponent(HomeScope::class)
interface HomeFlowComponent : FlowComponent {
  fun homeTabsFlowComponent(): HomeTabsFlowComponent
  fun cityDetailsFlowComponent(): CityDetailsFlowComponent

  @Provides
  @IntoSet
  fun emptyAssistedViewModelProviders(): @ScopedViewModel(HomeScope::class) AssistedViewModelProvider {
    return emptyAssistedViewModelProvider
  }

  @Provides
  @IntoSet
  fun emptyViewModelProviders(): @ScopedViewModel(HomeScope::class) ViewModelProvider {
    return emptyViewModelProvider
  }
}
