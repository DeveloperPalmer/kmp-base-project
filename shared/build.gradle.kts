import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  id("shared-convention")
}

// NOTE: Dependencies should be duplicated in framework export to include them in Framework
dependencies {
  commonMainApi(projects.shared.core.domain)
  commonMainApi(projects.shared.core.data)
  commonMainApi(projects.shared.core.ui)
  commonMainApi(projects.shared.core.uikit)

  commonMainApi(projects.shared.resources)

  commonMainApi(projects.shared.feature.app.ui)
}

kotlin {
  android {
    namespace = "com.kmpbaseproject.shared"
  }

  targets.withType<KotlinNativeTarget>().configureEach {
    binaries.framework {
      baseName = "MultiPlatformLibrary"

      export(projects.shared.core.domain)
      export(projects.shared.core.data)
      export(projects.shared.core.ui)
      export(projects.shared.core.uikit)

      export(projects.shared.resources)

      export(projects.shared.feature.app.ui)
    }
  }
}
