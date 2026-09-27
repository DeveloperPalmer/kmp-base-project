plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.cities.domain"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
