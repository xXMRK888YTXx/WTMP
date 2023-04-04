
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id(Deps.KaptPlugin.kapt)
}

android {
    namespace = "com.xxmrk888ytxx.coredeps"
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

        debug {
            isMinifyEnabled = Config.isR8ProGuardEnableForDebug
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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    api (Deps.AndroidX.AndroidCore)
    api (Deps.AndroidX.LifeCycle)
    api (Deps.AndroidX.ComposeActivity)
    testApi (Deps.Test.JUnit)
    androidTestApi (Deps.TestAndroid.AndroidJUnit)
    androidTestApi (Deps.TestAndroid.Espresso)
    testApi(Deps.Test.Mockk)
    testApi(Deps.TestAndroid.MockkAndroid)
    testApi(Deps.TestAndroid.MockkAgent)
    testApi(Deps.Test.Testing)
    androidTestApi(Deps.TestAndroid.MockkAndroid)
    androidTestApi(Deps.TestAndroid.MockkAgent)
    api (Deps.ViewModel.ViewModel)
    api (Deps.ViewModel.ViewModelKotlin)
    api (Deps.Dagger.DaggerCore)
    api(Deps.Coroutines.CoroutinesAndroid)
    api (Deps.Permissions.permission)
    api (Deps.Biometric.biometric)
    api (Deps.Fragment.fragment)

    implementation(Deps.Moshi.moshi)
    kapt(Deps.Moshi.moshiKaptPlugin)

    //Instrumental Test
    androidTestImplementation (Deps.InstrumentalTest.espresso)
    androidTestImplementation (Deps.InstrumentalTest.testRunner)
    androidTestImplementation (Deps.InstrumentalTest.testCore)
    androidTestImplementation (Deps.InstrumentalTest.jUnit)
    androidTestImplementation (Deps.InstrumentalTest.testRules)
    androidTestImplementation(Deps.TestAndroid.MockkAndroid)
    androidTestImplementation(Deps.TestAndroid.MockkAgent)
}