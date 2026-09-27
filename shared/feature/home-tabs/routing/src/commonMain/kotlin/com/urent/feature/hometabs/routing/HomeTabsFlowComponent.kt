package com.urent.feature.hometabs.routing

import androidx.compose.runtime.Stable
import com.urent.core.routing.di.FlowComponent
import com.urent.feature.cities.routing.CitiesFlowComponent
import com.urent.feature.hometabs.domain.di.HomeTabsScope
import com.urent.feature.map.routing.MapFlowComponent
import com.urent.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(HomeTabsScope::class)
interface HomeTabsFlowComponent : FlowComponent {
  fun citiesFlowComponent(): CitiesFlowComponent
  fun mapFlowComponent(): MapFlowComponent
}
