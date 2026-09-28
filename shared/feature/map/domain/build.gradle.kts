plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.feature.map.domain"
    withHostTest {}
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
  commonMainImplementation(libs.androidx.collection)
  commonTestImplementation(libs.bundles.unit.test)
}
