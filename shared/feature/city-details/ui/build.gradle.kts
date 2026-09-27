plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.feature.citydetails.ui"
  }
}

dependencies {
  commonMainApi(projects.shared.core.uikit)
  commonMainApi(projects.shared.feature.cityDetails.domain)
}
