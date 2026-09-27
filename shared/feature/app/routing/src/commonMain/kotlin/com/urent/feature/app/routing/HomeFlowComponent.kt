package com.urent.feature.app.routing

import androidx.compose.runtime.Stable
import com.urent.core.domain.di.HomeScope
import com.urent.core.routing.di.FlowComponent
import com.urent.feature.cities.routing.CitiesFlowComponent
import com.urent.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(HomeScope::class)
interface HomeFlowComponent : FlowComponent {
  fun citiesFlowComponent(): CitiesFlowComponent
}
