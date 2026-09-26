pluginManagement {
  includeBuild("build-conventions")
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "kmp-base-project"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
  ":android:app",
  ":shared",
  ":shared:resources",
  ":shared:core:domain",
  ":shared:core:data",
  ":shared:core:ui",
  ":shared:core:uikit",
  ":shared:core:component",
  ":shared:feature:app:ui",
)
