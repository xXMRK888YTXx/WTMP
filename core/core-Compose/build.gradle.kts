plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.xxmrk888ytxx.core_compose"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = Config.isR8ProGuardEnableForRelease
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }

        debug {
            isMinifyEnabled = Config.isR8ProGuardEnableForDebug
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = Config.sourceCompatibility
        targetCompatibility = Config.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.Compose.KotlinCompiler
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
    api (Deps.Compose.GoogleFonts)
    api (project(Project.core.core_Android.route))
    implementation(Deps.Coil.coil)
}