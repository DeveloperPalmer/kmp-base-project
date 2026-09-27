plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.app.ui"
  }
}

dependencies {
  commonMainApi(projects.shared.core.uikit)
  commonMainImplementation(projects.shared.lib.annotation)
}
