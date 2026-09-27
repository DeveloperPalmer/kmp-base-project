package com.kmpbaseproject.feature.home.routing

import androidx.compose.runtime.Stable
import com.kmpbaseproject.core.routing.di.FlowComponent
import com.kmpbaseproject.core.ui.viewmodel.AssistedViewModelProvider
import com.kmpbaseproject.core.ui.viewmodel.ViewModelProvider
import com.kmpbaseproject.core.ui.viewmodel.emptyAssistedViewModelProvider
import com.kmpbaseproject.core.ui.viewmodel.emptyViewModelProvider
import com.kmpbaseproject.feature.citydetails.routing.CityDetailsFlowComponent
import com.kmpbaseproject.feature.home.domain.di.HomeScope
import com.kmpbaseproject.feature.hometabs.routing.HomeTabsFlowComponent
import com.kmpbaseproject.lib.annotation.MergeSubcomponent
import com.kmpbaseproject.lib.annotation.ScopedViewModel
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
