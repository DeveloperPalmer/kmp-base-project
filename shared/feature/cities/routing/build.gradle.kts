plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.cities.routing"
  }
}

dependencies {
  commonMainApi(projects.shared.core.routing)
  commonMainApi(projects.shared.feature.cities.domain)
  commonMainApi(projects.shared.feature.cities.ui)
}
