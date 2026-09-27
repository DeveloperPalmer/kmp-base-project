package com.kmpbaseproject.feature.app.routing

import androidx.compose.runtime.Stable
import com.kmpbaseproject.core.domain.di.AppFlowScope
import com.kmpbaseproject.core.routing.di.FlowComponent
import com.kmpbaseproject.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(AppFlowScope::class)
interface AppFlowComponent : FlowComponent
