package com.kmpbaseproject.feature.citydetails.routing

import androidx.compose.runtime.Stable
import com.kmpbaseproject.core.routing.di.FlowComponent
import com.kmpbaseproject.feature.citydetails.domain.di.CityDetailsScope
import com.kmpbaseproject.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(CityDetailsScope::class)
interface CityDetailsFlowComponent : FlowComponent
