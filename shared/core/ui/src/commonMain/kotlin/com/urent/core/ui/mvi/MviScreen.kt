package com.urent.core.ui.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun <S : Any, I : Any, SE : Any> MviScreen(
  viewModel: BaseViewModel<S, I, SE>,
  content: @Composable MviScreenScope<SE>.(state: S, onIntent: (I) -> Unit) -> Unit,
) {
  val state by viewModel.collectAsState()
  val scope = remember(viewModel) {
    object : MviScreenScope<SE> {
      override val sideEffects = viewModel.container.refCountSideEffectFlow
    }
  }
  scope.content(state, viewModel::dispatch)
}
