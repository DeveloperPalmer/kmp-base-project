package com.urent.feature.app.routing

import androidx.compose.runtime.Stable
import com.urent.core.domain.di.AppFlowScope
import com.urent.core.routing.di.FlowComponent
import com.urent.core.ui.viewmodel.AssistedViewModelProvider
import com.urent.core.ui.viewmodel.ViewModelProvider
import com.urent.core.ui.viewmodel.emptyAssistedViewModelProvider
import com.urent.core.ui.viewmodel.emptyViewModelProvider
import com.urent.lib.annotation.MergeSubcomponent
import com.urent.lib.annotation.ScopedViewModel
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

@Stable
@MergeSubcomponent(AppFlowScope::class)
interface AppFlowComponent : FlowComponent {
  fun homeFlowComponent(): HomeFlowComponent

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
