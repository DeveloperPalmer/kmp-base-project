plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.map.ui"
  }

  sourceSets {
    androidMain.dependencies {
      implementation(libs.yandex.mapkit)
    }
  }
}

dependencies {
  commonMainApi(projects.shared.core.uikit)
  commonMainApi(projects.shared.feature.map.domain)
}
