package com.urent.feature.app.routing

import androidx.compose.runtime.Stable
import com.urent.core.domain.di.AppFlowScope
import com.urent.core.routing.di.FlowComponent
import com.urent.lib.annotation.MergeSubcomponent

@Stable
@MergeSubcomponent(AppFlowScope::class)
interface AppFlowComponent : FlowComponent
