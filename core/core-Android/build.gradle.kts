plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.xxmrk888ytxx.coredeps"
    compileSdk = libs.versions.compileSdk.get().toInt()
    setFlavorDimensions(listOf("WTMP"))

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }

        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    productFlavors {
        // It contains a feature for tracking the opening of selected applications,
        // which was cut out due to the fact that Google play did not allow me
        // to release this application with android permission.permission.QUERY_ALL_PACKAGES.
        // Google play КОНТОРА ПИДАРАСОВ!!!

        create("googlePlay") {
            dimension = "WTMP"
        }


        create("notGooglePlay") {
            dimension = "WTMP"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
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