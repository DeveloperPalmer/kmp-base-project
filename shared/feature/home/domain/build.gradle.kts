plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.home.domain"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
