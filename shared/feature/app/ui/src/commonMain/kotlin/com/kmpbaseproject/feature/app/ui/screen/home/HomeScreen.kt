package com.kmpbaseproject.feature.app.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kmpbaseproject.core.ui.mvi.MviScreen
import com.kmpbaseproject.uikit.bottombar.BottomBar
import com.kmpbaseproject.uikit.bottombar.BottomBarItem
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(viewModel: HomeViewModel, selectedTab: Tab, content: @Composable () -> Unit) {
  MviScreen(viewModel) { _, onIntent ->
    Column(modifier = Modifier.fillMaxSize()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
      ) {
        content()
      }
      BottomBar {
        Tab.entries.forEach { tab ->
          BottomBarItem(
            selected = tab == selectedTab,
            onClick = { onIntent(ViewIntent.SelectTab(tab)) },
            icon = tab.icon,
            contentDescription = stringResource(tab.title),
          )
        }
      }
    }
  }
}
