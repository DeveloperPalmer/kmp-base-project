plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.core.data"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
