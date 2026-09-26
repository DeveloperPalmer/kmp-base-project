package com.urent.core.component

import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface IosAppComponent : AppComponent

@MergeComponent.CreateComponent
expect fun createIosAppComponent(): IosAppComponent
