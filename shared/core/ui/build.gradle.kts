plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.core.ui"
    withHostTest {}
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
  commonMainApi(projects.shared.resources)
  commonMainApi(libs.bundles.orbit)
  commonTestImplementation(libs.bundles.unit.test)
}
