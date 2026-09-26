import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  id("shared-convention")
}

// NOTE: Dependencies should be duplicated in framework export to include them in Framework
dependencies {
  commonMainApi(projects.shared.core.domain)
  commonMainApi(projects.shared.core.data)
}

kotlin {
  android {
    namespace = "com.urent.shared"
  }

  targets.withType<KotlinNativeTarget>().configureEach {
    binaries.framework {
      baseName = "MultiPlatformLibrary"

      export(projects.shared.core.domain)
      export(projects.shared.core.data)
    }
  }
}
