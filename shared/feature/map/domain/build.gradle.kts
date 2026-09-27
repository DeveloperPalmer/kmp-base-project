plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.feature.map.domain"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
