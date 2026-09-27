package com.kmpbaseproject.core.routing.di

import androidx.compose.runtime.Stable
import com.kmpbaseproject.core.ui.viewmodel.AssistedViewModelProvider
import com.kmpbaseproject.core.ui.viewmodel.ViewModelProvider
import kotlinx.coroutines.CoroutineScope

@Stable
interface FlowComponent {
  // VMs registered in a given FlowScope
  fun viewModelProviders(): Set<ViewModelProvider>
  fun assistedViewModelProviders(): Set<AssistedViewModelProvider>

  // VMs registered in a given FlowScope + its parent FlowScopes
  fun flowViewModelProviders(): Set<ViewModelProvider>
  fun assistedFlowViewModelProviders(): Set<AssistedViewModelProvider>

  fun coroutineScope(): CoroutineScope
}
