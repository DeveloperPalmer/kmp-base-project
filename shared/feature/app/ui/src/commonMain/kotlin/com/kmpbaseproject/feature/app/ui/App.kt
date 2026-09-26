package com.kmpbaseproject.feature.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kmpbaseproject.resources.Res
import com.kmpbaseproject.resources.app_name
import com.kmpbaseproject.uikit.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
  AppTheme {
    Surface(modifier = Modifier.fillMaxSize()) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = stringResource(Res.string.app_name),
          style = MaterialTheme.typography.headlineMedium
        )
      }
    }
  }
}
