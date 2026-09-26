package com.urent.sample

import android.app.Application
import com.urent.core.component.AndroidAppComponent
import com.urent.core.component.AppComponent
import com.urent.core.component.AppComponentHolder
import com.urent.core.component.create

class ApplicationDelegate :
  Application(),
  AppComponentHolder {
  private var _appComponent: AppComponent? = null

  override fun onCreate() {
    _appComponent = buildAppComponent()
    super.onCreate()
  }

  override val appComponent: AppComponent
    get() = _appComponent ?: error("app component no provided")

  private fun buildAppComponent(): AppComponent {
    return AndroidAppComponent::class.create()
  }
}
