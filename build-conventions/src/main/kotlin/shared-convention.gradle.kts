import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  kotlin("multiplatform")
  id("com.android.kotlin.multiplatform.library")
  id("com.google.devtools.ksp")
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

dependencies {
  val kotlinInjectCompiler = versionCatalogs
    .named("libs")
    .findLibrary("kotlin.inject.compiler")
    .get()
  val anvilCompiler = versionCatalogs
    .named("libs")
    .findLibrary("anvil.compiler")
    .get()

  kotlin.targets
    .matching { it.name != "metadata" }
    .configureEach {
      val configuration = "ksp" + name.replaceFirstChar(Char::uppercaseChar)
      add(configuration, kotlinInjectCompiler)
      add(configuration, anvilCompiler)
    }
}
