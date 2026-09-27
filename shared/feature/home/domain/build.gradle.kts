plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.feature.home.domain"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
