plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.map.domain"
    withHostTest {}
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
  commonTestImplementation(libs.bundles.unit.test)
}
