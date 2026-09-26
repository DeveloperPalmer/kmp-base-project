plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.urent.core.data"
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)
}
