package com.urent.feature.map.ui.adapter

import com.urent.feature.map.domain.di.MapScope
import com.urent.feature.map.ui.entity.MapAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(MapScope::class)
@ContributesBinding(MapScope::class)
class MapAdapterImpl : MapAdapter {
  private val commandsFlow = MutableSharedFlow<MapAction>(extraBufferCapacity = 3)
  override val actions: Flow<MapAction> = commandsFlow

  override fun accept(action: MapAction) {
    commandsFlow.tryEmit(action)
  }
}
