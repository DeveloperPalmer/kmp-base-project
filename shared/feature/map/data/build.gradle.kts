plugins {
  id("shared-convention")
  kotlin("plugin.serialization")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.feature.map.data"
  }
}

dependencies {
  commonMainApi(projects.shared.core.data)
  commonMainApi(projects.shared.feature.map.domain)
}
