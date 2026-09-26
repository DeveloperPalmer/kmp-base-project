plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.plugin.compose")
  id("detekt-convention")
}

android {
  namespace = "com.kmpbaseproject.sample"
  compileSdk {
    version = release(37)
  }

  defaultConfig {
    applicationId = "com.kmpbaseproject.sample"
    minSdk = 26
    targetSdk = 37
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      optimization {
        enable = false
      }
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildFeatures.compose = true
}

dependencies {
  implementation(projects.shared.core.component)
  implementation(projects.shared.feature.app.ui)

  implementation(libs.androidx.activity.compose)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}
