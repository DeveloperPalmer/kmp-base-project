plugins {
  id("shared-convention")
  kotlin("plugin.serialization")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.core.data"
  }

  sourceSets {
    androidMain.dependencies {
      implementation(libs.ktor.okhttp)
    }
    iosMain.dependencies {
      implementation(libs.ktor.darwin)
    }
  }
}

dependencies {
  commonMainApi(projects.shared.core.domain)

  commonMainApi(libs.ktor)
  commonMainApi(libs.ktor.logging)
  commonMainImplementation(libs.ktor.content)
  commonMainImplementation(libs.ktor.json)
}
