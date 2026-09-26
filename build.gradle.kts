plugins {
  // Load convention plugins (and AGP/KGP they bring) once in the root classloader,
  // so all subprojects share the same plugin classes.
  id("shared-convention") apply false
  alias(libs.plugins.spotless)
}

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*.*")
    ktlint(libs.versions.ktlint.get())
    trimTrailingWhitespace()
    endWithNewline()
  }

  kotlinGradle {
    target("**/*.gradle.kts")
    targetExclude("**/build/**/*.*")
    ktlint(libs.versions.ktlint.get())
    trimTrailingWhitespace()
    endWithNewline()
  }
}
