package com.kmpbaseproject.feature.cities.routing

import androidx.compose.runtime.Stable
import com.kmpbaseproject.core.routing.di.FlowComponent
import com.kmpbaseproject.feature.cities.domain.di.CitiesScope
import com.kmpbaseproject.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(CitiesScope::class)
interface CitiesFlowComponent : FlowComponent
