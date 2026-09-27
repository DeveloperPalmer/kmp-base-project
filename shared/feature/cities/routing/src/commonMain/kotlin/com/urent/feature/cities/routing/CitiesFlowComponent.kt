package com.urent.feature.cities.routing

import androidx.compose.runtime.Stable
import com.urent.core.routing.di.FlowComponent
import com.urent.feature.cities.domain.di.CitiesScope
import com.urent.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(CitiesScope::class)
interface CitiesFlowComponent : FlowComponent
