package com.kmpbaseproject.sample

import android.app.Application
import com.kmpbaseproject.core.component.AndroidAppComponent
import com.kmpbaseproject.core.component.AppComponent
import com.kmpbaseproject.core.component.AppComponentHolder
import com.kmpbaseproject.core.component.create
import com.kmpbaseproject.core.component.createBuildConfiguration
import com.yandex.mapkit.MapKitFactory

class ApplicationDelegate :
  Application(),
  AppComponentHolder {
  private var _appComponent: AppComponent? = null

  override fun onCreate() {
    _appComponent = buildAppComponent()
    super.onCreate()
    initMapKit()
  }

  override val appComponent: AppComponent
    get() = _appComponent ?: error("app component no provided")

  private fun buildAppComponent(): AppComponent {
    val buildConfiguration = createBuildConfiguration(
      buildType = BuildConfig.BUILD_TYPE,
      versionName = BuildConfig.VERSION_NAME,
    )
    return AndroidAppComponent::class.create(
      contextDelegate = this,
      buildConfigurationDelegate = buildConfiguration,
    )
  }

  private fun initMapKit() {
    MapKitFactory.setApiKey(BuildConfig.YANDEX_API_KEY)
    MapKitFactory.initialize(this)
  }
}
