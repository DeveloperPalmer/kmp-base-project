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
  commonMainApi(projects.shared.feature.homeTabs.routing)
}
