package com.urent.feature.citydetails.routing

import androidx.compose.runtime.Stable
import com.urent.core.domain.AppLauncher
import com.urent.core.routing.di.FlowComponent
import com.urent.feature.citydetails.domain.di.CityDetailsScope
import com.urent.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(CityDetailsScope::class)
interface CityDetailsFlowComponent : FlowComponent {
  fun appLauncher(): AppLauncher
}
