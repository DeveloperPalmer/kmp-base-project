import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  kotlin("jvm")
  id("com.google.devtools.ksp")
}

kotlin {
  jvmToolchain(17)

  compilerOptions {
    jvmTarget = JvmTarget.JVM_17
  }
}

dependencies {
  implementation(projects.shared.lib.annotation)
  implementation(libs.kotlin.inject.runtime)
  implementation(libs.anvil.runtime)
  implementation(libs.anvil.runtime.optional)
  implementation(libs.ksp.processor.api)
  implementation(libs.kotlin.coroutines.core)
  implementation(libs.kotlin.poet)
  implementation(libs.kotlin.poet.ksp)
  implementation(libs.auto.service.annotations)

  ksp(libs.kotlin.inject.compiler)
  ksp(libs.anvil.compiler)
  ksp(libs.auto.service)
}
