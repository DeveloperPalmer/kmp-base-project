package com.kmpbaseproject.sample

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import com.arkivanov.decompose.defaultComponentContext
import com.kmpbaseproject.core.component.AndroidAppComponent
import com.kmpbaseproject.core.component.AppComponentHolder
import com.kmpbaseproject.feature.app.routing.decompose.AppFlow
import com.kmpbaseproject.feature.app.routing.decompose.AppFlowNavigationComponent
import com.kmpbaseproject.uikit.theme.AppTheme

class MainActivity : ComponentActivity() {
  // Without the permission foreground work still runs, only its notification is hidden.
  // Lint sees Fragment 1.0.0 from MapKit on the compile classpath, but this is not a FragmentActivity
  @SuppressLint("InvalidFragmentVersionForActivityResult")
  private val notificationPermissionRequest = registerForActivityResult(RequestPermission()) {}

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    val appComponent = (applicationContext as AppComponentHolder).appComponent as AndroidAppComponent
    val foregroundComponent = appComponent
      .foregroundComponentFactory()
      .create()
    val appFlowNavigationComponent = AppFlowNavigationComponent(
      component = foregroundComponent.appFlowComponent(),
      context = defaultComponentContext(),
    )
    requestNotificationPermission()
    setContent {
      AppTheme {
        AppFlow(
          component = appFlowNavigationComponent
        )
      }
    }
  }

  private fun requestNotificationPermission() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    val permission = Manifest.permission.POST_NOTIFICATIONS
    if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
      notificationPermissionRequest.launch(permission)
    }
  }
}
