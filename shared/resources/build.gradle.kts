plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.resources"
    androidResources {
      enable = true
    }
  }
}

compose.resources {
  publicResClass = true
  packageOfResClass = "com.kmpbaseproject.resources"
  generateResClass = always
}
