plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.core.ui"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
  commonMainApi(projects.shared.resources)
  commonMainApi(libs.bundles.orbit)
}
