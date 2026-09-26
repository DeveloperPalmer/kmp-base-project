plugins {
  id("shared-convention")
}

kotlin {
  android {
    namespace = "com.urent.core.domain"
  }
}

dependencies {
  commonMainApi(libs.kotlin.inject.runtime)
  commonMainApi(libs.anvil.runtime)
  commonMainApi(libs.anvil.runtime.optional)
}
