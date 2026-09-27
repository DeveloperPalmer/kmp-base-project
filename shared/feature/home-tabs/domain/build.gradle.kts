plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.feature.hometabs.domain"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
