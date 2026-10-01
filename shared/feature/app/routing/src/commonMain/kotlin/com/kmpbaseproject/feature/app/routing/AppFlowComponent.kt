package com.kmpbaseproject.feature.app.routing

import androidx.compose.runtime.Stable
import com.kmpbaseproject.core.domain.di.AppFlowScope
import com.kmpbaseproject.core.routing.di.FlowComponent
import com.kmpbaseproject.core.ui.viewmodel.AssistedViewModelProvider
import com.kmpbaseproject.core.ui.viewmodel.ViewModelProvider
import com.kmpbaseproject.core.ui.viewmodel.emptyAssistedViewModelProvider
import com.kmpbaseproject.core.ui.viewmodel.emptyViewModelProvider
import com.kmpbaseproject.feature.hometabs.routing.HomeTabsFlowComponent
import com.kmpbaseproject.lib.annotation.MergeSubcomponent
import com.kmpbaseproject.lib.annotation.ScopedViewModel
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

@Stable
@MergeSubcomponent(AppFlowScope::class)
interface AppFlowComponent : FlowComponent {
  fun homeTabsFlowComponent(): HomeTabsFlowComponent

  @Provides
  @IntoSet
  fun emptyAssistedViewModelProviders(): @ScopedViewModel(AppFlowScope::class) AssistedViewModelProvider {
    return emptyAssistedViewModelProvider
  }

  @Provides
  @IntoSet
  fun emptyViewModelProviders(): @ScopedViewModel(AppFlowScope::class) ViewModelProvider {
    return emptyViewModelProvider
  }
}
