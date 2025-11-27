plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "com.xxmrk888ytxx.database"
    compileSdk = libs.versions.compileSdk.get().toInt()
    setFlavorDimensions(listOf("WTMP"))

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        room {
            schemaDirectory("$projectDir/schemas")
        }
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
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(project(":core:core-Android"))
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)

    ksp(libs.room.compiler)
    ksp(libs.dagger.compiler)
    //Instrumental Test
    androidTestImplementation(libs.instrumental.espresso)
    androidTestImplementation(libs.instrumental.test.runner)
    androidTestImplementation(libs.instrumental.test.core)
    androidTestImplementation(libs.instrumental.junit.ktx)
    androidTestImplementation(libs.instrumental.test.rules)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.agent)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.room.testing)

}