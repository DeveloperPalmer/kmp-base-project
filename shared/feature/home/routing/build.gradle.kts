plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.feature.home.routing"
  }
}

dependencies {
  commonMainApi(projects.shared.core.routing)
  commonMainApi(projects.shared.feature.home.domain)
  commonMainApi(projects.shared.feature.homeTabs.routing)
}
