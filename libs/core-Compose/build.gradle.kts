plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.xxmrk888ytxx.core_compose"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.compileSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = Config.isR8ProGuardEnableForRelease
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    androidTestApi (Deps.Compose.TestJUnit)
    debugApi (Deps.Compose.TestTooling)
    debugApi (Deps.Compose.TestManifest)
    api (Deps.Compose.UI)
    api (Deps.Compose.Tooling)
    api (Deps.Compose.Material)
    api (Deps.Compose.Navigation)
    api (project(Project.libs.core.route))
}