plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.feature.citydetails.domain"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
