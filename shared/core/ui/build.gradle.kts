plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.core.ui"
    withHostTest {}
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
  commonMainApi(projects.shared.resources)
  commonMainApi(libs.bundles.orbit)
  commonMainApi(libs.paging.common)
  commonMainImplementation(libs.essenty.back.handler)
  commonTestImplementation(libs.bundles.unit.test)
}
