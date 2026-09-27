plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.citydetails.domain"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
