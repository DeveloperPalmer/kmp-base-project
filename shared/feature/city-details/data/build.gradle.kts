plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.citydetails.data"
  }
}

dependencies {
  commonMainApi(projects.shared.core.data)
  commonMainApi(projects.shared.feature.cityDetails.domain)
}
