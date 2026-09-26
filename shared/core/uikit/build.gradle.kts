plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.core.uikit"
  }
}

dependencies {
  commonMainApi(projects.shared.core.ui)
  commonMainApi(projects.shared.resources)
}
