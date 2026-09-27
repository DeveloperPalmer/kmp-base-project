plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.feature.app.routing"
  }
}

dependencies {
  commonMainApi(projects.shared.core.routing)
  commonMainApi(projects.shared.feature.app.ui)
  commonMainApi(projects.shared.feature.cities.routing)
  commonMainApi(projects.shared.feature.map.routing)
}
