package com.kmpbaseproject.feature.hometabs.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kmpbaseproject.core.ui.mvi.MviScreen
import com.kmpbaseproject.feature.hometabs.ui.entity.Tab
import com.kmpbaseproject.uikit.component.bottombar.BottomBar
import com.kmpbaseproject.uikit.component.bottombar.BottomBarItem
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(viewModel: HomeViewModel, selectedTab: Tab, content: @Composable () -> Unit) {
  MviScreen(viewModel) { _, onIntent ->
    val density = LocalDensity.current
    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    Column(modifier = Modifier.fillMaxSize()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .consumeWindowInsets(PaddingValues(bottom = bottomBarHeight)),
      ) {
        content()
      }
      BottomBar(
        modifier = Modifier.onSizeChanged { size ->
          bottomBarHeight = with(density) { size.height.toDp() }
        },
      ) {
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
