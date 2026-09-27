plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.app.routing"
  }
}

dependencies {
  commonMainApi(projects.shared.core.routing)
  commonMainApi(projects.shared.feature.app.ui)
  commonMainApi(projects.shared.feature.cities.routing)
}
