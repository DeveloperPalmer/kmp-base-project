plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.core.routing"
  }
}

dependencies {
  commonMainApi(libs.bundles.decompose)
}
