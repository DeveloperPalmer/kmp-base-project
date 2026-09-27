package com.urent.core.component

import com.urent.core.domain.di.ForegroundScope
import com.urent.core.ui.viewmodel.AssistedViewModelProvider
import com.urent.core.ui.viewmodel.ViewModelProvider
import com.urent.core.ui.viewmodel.emptyAssistedViewModelProvider
import com.urent.core.ui.viewmodel.emptyViewModelProvider
import com.urent.feature.app.routing.AppFlowComponent
import com.urent.lib.annotation.MergedViewModels
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface ForegroundComponent {
  fun appFlowComponent(): AppFlowComponent

  @Provides
  @IntoSet
  fun emptyMergedViewModelProviders(): @MergedViewModels(ForegroundScope::class) ViewModelProvider {
    return emptyViewModelProvider
  }

  @Provides
  @IntoSet
  fun emptyMergedAssistedViewModelProviders(): @MergedViewModels(ForegroundScope::class) AssistedViewModelProvider {
    return emptyAssistedViewModelProvider
  }
}
