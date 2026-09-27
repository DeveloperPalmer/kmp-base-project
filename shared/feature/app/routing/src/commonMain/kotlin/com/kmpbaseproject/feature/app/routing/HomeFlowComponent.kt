package com.kmpbaseproject.feature.app.routing

import androidx.compose.runtime.Stable
import com.kmpbaseproject.core.domain.di.HomeScope
import com.kmpbaseproject.core.routing.di.FlowComponent
import com.kmpbaseproject.feature.cities.routing.CitiesFlowComponent
import com.kmpbaseproject.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(HomeScope::class)
interface HomeFlowComponent : FlowComponent {
  fun citiesFlowComponent(): CitiesFlowComponent
}
