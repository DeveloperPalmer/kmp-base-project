plugins {
  // Load convention plugins (and AGP/KGP/Compose plugins they bring) once in the root classloader,
  // so all subprojects share the same plugin classes.
  id("shared-convention") apply false
  id("compose-convention") apply false
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

val detektTaskNames = setOf(
  // android app
  "detektDebug",
  // KMP modules: commonMain + androidMain with type resolution, iosMain without it
  "detektMainAndroid",
  "detektIosMainSourceSet",
)

subprojects {
  tasks.matching { it.name in detektTaskNames }.configureEach {
    mustRunAfter(rootProject.tasks.named("spotlessApply"))
  }
}

tasks.register("prePushCheck") {
  group = "verification"
  dependsOn("spotlessApply")
  dependsOn(subprojects.map { project -> project.tasks.matching { it.name in detektTaskNames } })
}
