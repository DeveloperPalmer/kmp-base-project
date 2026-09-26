package com.kmpbaseproject.sample

import android.app.Application
import com.kmpbaseproject.core.component.AndroidAppComponent
import com.kmpbaseproject.core.component.AppComponent
import com.kmpbaseproject.core.component.AppComponentHolder
import com.kmpbaseproject.core.component.create

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
