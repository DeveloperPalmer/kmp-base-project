plugins {
  id("shared-convention")
  kotlin("plugin.serialization")
}

kotlin {
  android {
    namespace = "com.urent.feature.cities.data"
  }
}

dependencies {
  commonMainApi(projects.shared.core.data)
  commonMainApi(projects.shared.feature.cities.domain)

  commonMainImplementation(libs.sqldelight.paging)
}
