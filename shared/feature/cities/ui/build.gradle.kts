plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.cities.ui"
  }
}

dependencies {
  commonMainApi(projects.shared.core.uikit)
  commonMainApi(projects.shared.feature.cities.domain)

  commonMainImplementation(libs.paging.compose)
}
