package com.kmpbaseproject.feature.map.routing

import androidx.compose.runtime.Stable
import com.kmpbaseproject.core.domain.AppLauncher
import com.kmpbaseproject.core.routing.di.FlowComponent
import com.kmpbaseproject.feature.map.domain.di.MapScope
import com.kmpbaseproject.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(MapScope::class)
interface MapFlowComponent : FlowComponent {
  fun appLauncher(): AppLauncher
}
