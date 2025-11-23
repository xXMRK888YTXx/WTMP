plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.xxmrk888ytxx.coredeps"
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    api(libs.androidx.core)
    api(libs.androidx.lifecycle)
    api(libs.androidx.activity.compose)
    testApi(libs.junit)
    androidTestApi(libs.android.junit)
    androidTestApi(libs.espresso.core)
    testApi(libs.mockk)
    testApi(libs.mockk.android)
    testApi(libs.mockk.agent)
    testApi(libs.testng)
    androidTestApi(libs.mockk.android)
    androidTestApi(libs.mockk.agent)
    api(libs.viewmodel)
    api(libs.viewmodel.ktx)
    api(libs.dagger)
    api(libs.coroutines.android)
    api(libs.permissions)
    api(libs.biometric)
    api(libs.fragment)
    api(libs.immutable.collections)

    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    //Instrumental Test
    androidTestImplementation(libs.instrumental.espresso)
    androidTestImplementation(libs.instrumental.test.runner)
    androidTestImplementation(libs.instrumental.test.core)
    androidTestImplementation(libs.instrumental.junit.ktx)
    androidTestImplementation(libs.instrumental.test.rules)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.agent)
}