plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.citydetails.routing"
  }
}

dependencies {
  commonMainApi(projects.shared.core.routing)
  commonMainApi(projects.shared.feature.cityDetails.data)
  commonMainApi(projects.shared.feature.cityDetails.domain)
  commonMainApi(projects.shared.feature.cityDetails.ui)

  commonMainImplementation(libs.ktor.http)
}
