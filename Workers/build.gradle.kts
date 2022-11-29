plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id(Deps.Dagger.DaggerKaptPlugin)
}

android {
    namespace = "com.xxmrk888ytxx.workers"
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
    implementation(project(Project.core.core_Android.route))
    kapt (Deps.Dagger.DaggerKaptCompiler)
    implementation(Deps.WorkManager.workManager)
    androidTestImplementation (Deps.WorkManager.workManagerTest)
    //test
    testImplementation(Deps.TestAndroid.MockkAndroid)
    testImplementation(Deps.TestAndroid.MockkAgent)
    testImplementation(Deps.Test.Testing)

    androidTestImplementation(Deps.TestAndroid.MockkAndroid)
    androidTestImplementation(Deps.TestAndroid.MockkAgent)
    androidTestImplementation(Deps.Test.Testing)
}