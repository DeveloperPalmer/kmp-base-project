package com.kmpbaseproject.feature.app.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kmpbaseproject.core.ui.mvi.MviScreen

@Composable
fun HomeScreen(viewModel: HomeViewModel, content: @Composable () -> Unit) {
  MviScreen(viewModel) { _, _ ->
    Box(modifier = Modifier.fillMaxSize()) {
      content()
    }
  }
}
