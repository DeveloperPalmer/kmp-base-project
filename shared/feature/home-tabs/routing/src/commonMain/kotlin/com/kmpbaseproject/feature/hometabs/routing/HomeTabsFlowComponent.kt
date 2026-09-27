package com.kmpbaseproject.feature.hometabs.routing

import androidx.compose.runtime.Stable
import com.kmpbaseproject.core.routing.di.FlowComponent
import com.kmpbaseproject.feature.cities.routing.CitiesFlowComponent
import com.kmpbaseproject.feature.hometabs.domain.di.HomeTabsScope
import com.kmpbaseproject.feature.map.routing.MapFlowComponent
import com.kmpbaseproject.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(HomeTabsScope::class)
interface HomeTabsFlowComponent : FlowComponent {
  fun citiesFlowComponent(): CitiesFlowComponent
  fun mapFlowComponent(): MapFlowComponent
}
