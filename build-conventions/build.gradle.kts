plugins {
  `kotlin-dsl`
}

dependencies {
  implementation(libs.ksp.plugin)
  implementation(libs.android.gradle.plugin)
  implementation(libs.kotlin.plugin)
  implementation(libs.kotlin.compose.plugin)
  implementation(libs.kotlin.serialization.plugin)
  implementation(libs.compose.plugin)
  implementation(libs.detekt.plugin)
}
