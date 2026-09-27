plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.hometabs.domain"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
