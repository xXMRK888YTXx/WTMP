plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id (Deps.Dagger.DaggerKaptPlugin)
}

android {
    namespace = "com.xxmrk888ytxx.api_telegram"
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
    api (Deps.Retrofit2.retrofit)
    implementation (Deps.Retrofit2.jsonConverter)
    implementation (Deps.Retrofit2.loggingInterceptor)
    testImplementation(Deps.Retrofit2.Test.MockServer)
    kapt (Deps.Dagger.DaggerKaptCompiler)

    //test
    testImplementation(Deps.TestAndroid.MockkAndroid)
    testImplementation(Deps.TestAndroid.MockkAgent)
    testImplementation(Deps.Test.Testing)
}