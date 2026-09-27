plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.map.domain"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
