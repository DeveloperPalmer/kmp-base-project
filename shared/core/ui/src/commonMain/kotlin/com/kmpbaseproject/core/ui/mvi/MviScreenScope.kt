package com.kmpbaseproject.core.ui.mvi

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow

@Stable
interface MviScreenScope<SE : Any> {
  val sideEffects: Flow<SE>
}
