package com.urent.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.urent.core.component.AndroidAppComponent
import com.urent.core.component.AppComponentHolder
import com.urent.feature.app.routing.decompose.AppFlow
import com.urent.feature.app.routing.decompose.AppFlowNavigationComponent

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    val appComponent = (applicationContext as AppComponentHolder).appComponent as AndroidAppComponent
    val foregroundComponent = appComponent
      .foregroundComponentFactory()
      .create()
    val appFlowNavigationComponent = AppFlowNavigationComponent(
      component = foregroundComponent.appFlowComponentFactory().create(),
      context = defaultComponentContext(),
    )
    setContent {
      AppFlow(
        component = appFlowNavigationComponent
      )
    }
  }
}
