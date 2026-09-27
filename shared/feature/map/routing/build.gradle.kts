plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.feature.map.routing"
  }
}

dependencies {
  commonMainApi(projects.shared.core.routing)
  commonMainApi(projects.shared.feature.map.domain)
  commonMainApi(projects.shared.feature.map.ui)
}
