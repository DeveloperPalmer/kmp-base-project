package com.urent.core.ui.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun <S : Any, I : Any, SE : Any> MviScreen(
  viewModel: BaseViewModel<S, I, SE>,
  content: @Composable (state: S, onIntent: (I) -> Unit) -> Unit,
) {
  val state by viewModel.collectAsState()
  content(state, viewModel::dispatch)
}
