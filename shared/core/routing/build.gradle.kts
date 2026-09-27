plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.core.routing"
  }
}

dependencies {
  commonMainApi(libs.bundles.decompose)
}
