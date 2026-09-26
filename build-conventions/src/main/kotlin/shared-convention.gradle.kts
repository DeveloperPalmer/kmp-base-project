import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  kotlin("multiplatform")
  id("com.android.kotlin.multiplatform.library")
}

kotlin {
  android {
    compileSdk = 37
    minSdk = 26

    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_17)
    }
  }

  iosArm64()
  iosSimulatorArm64()
}
