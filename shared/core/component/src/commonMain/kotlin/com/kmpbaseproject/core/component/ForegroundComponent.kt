package com.kmpbaseproject.core.component

import com.kmpbaseproject.core.domain.di.ForegroundScope
import com.kmpbaseproject.core.ui.viewmodel.AssistedViewModelProvider
import com.kmpbaseproject.core.ui.viewmodel.ViewModelProvider
import com.kmpbaseproject.core.ui.viewmodel.emptyAssistedViewModelProvider
import com.kmpbaseproject.core.ui.viewmodel.emptyViewModelProvider
import com.kmpbaseproject.feature.app.routing.AppFlowComponent
import com.kmpbaseproject.lib.annotation.MergedViewModels
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
