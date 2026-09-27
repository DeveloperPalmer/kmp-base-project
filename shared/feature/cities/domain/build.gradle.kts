plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.feature.cities.domain"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)

  commonMainApi(libs.paging.common)
}
