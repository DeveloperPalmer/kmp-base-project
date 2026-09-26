plugins {
  id("shared-convention")
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
  commonMainImplementation(libs.ktor.content)
  commonMainImplementation(libs.ktor.json)
}
