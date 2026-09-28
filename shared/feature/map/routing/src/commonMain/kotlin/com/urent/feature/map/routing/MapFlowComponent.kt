package com.urent.feature.map.routing

import androidx.compose.runtime.Stable
import com.urent.core.domain.AppLauncher
import com.urent.core.routing.di.FlowComponent
import com.urent.feature.map.domain.di.MapScope
import com.urent.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(MapScope::class)
interface MapFlowComponent : FlowComponent {
  fun appLauncher(): AppLauncher
}
