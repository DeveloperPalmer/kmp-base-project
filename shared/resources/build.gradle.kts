plugins {
  id("compose-convention")
}

kotlin {
  android {
    namespace = "com.urent.resources"
    androidResources {
      enable = true
    }
  }
}

compose.resources {
  publicResClass = true
  packageOfResClass = "com.urent.resources"
  generateResClass = always
}
