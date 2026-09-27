plugins {
  id("shared-convention")
  kotlin("plugin.compose")
  id("org.jetbrains.compose")
}

dependencies {
  commonMainApi(versionCatalogs.named("libs").findBundle("compose").get())
  androidRuntimeClasspath(versionCatalogs.named("libs").findLibrary("compose.ui.tooling").get())
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
    freeCompilerArgs.add("-opt-in=androidx.compose.foundation.ExperimentalFoundationApi")
    freeCompilerArgs.add("-opt-in=androidx.compose.ui.ExperimentalComposeUiApi")
    freeCompilerArgs.add("-opt-in=androidx.compose.ui.text.ExperimentalTextApi")
    freeCompilerArgs.add("-opt-in=androidx.compose.animation.ExperimentalAnimationApi")
  }
}

composeCompiler {
  stabilityConfigurationFiles.add(rootProject.layout.projectDirectory.file("stability_config.conf"))
}
