plugins {
  `kotlin-dsl`
}

dependencies {
  implementation(libs.ksp.plugin)
  implementation(libs.android.gradle.plugin)
  implementation(libs.kotlin.plugin)
  implementation(libs.kotlin.compose.plugin)
  implementation(libs.compose.plugin)
}
