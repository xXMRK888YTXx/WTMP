plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.guardsquare)
    alias(libs.plugins.kotlin.compose)

}

android {
    namespace = libs.versions.packageName.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    setFlavorDimensions(listOf("WTMP"))

    defaultConfig {
        applicationId = libs.versions.packageName.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()
        versionCode = 24
        versionName = "1.3.2r"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = libs.versions.isR8ProGuardEnableForRelease.get().toBoolean()
            isShrinkResources = libs.versions.isR8ProGuardEnableForRelease.get().toBoolean()
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            testProguardFile("test-proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            isMinifyEnabled = libs.versions.isR8ProGuardEnableForDebug.get().toBoolean()
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
            versionNameSuffix = "-gh"
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        resources.excludes.add("META-INF/*")
    }
}
dependencies {
    implementation(project(":core:core-Compose"))
    implementation(project(":AdminReceiver"))
    implementation(project(":Camera"))
    implementation(project(":Api-Telegram"))
    implementation(project(":Workers"))
    implementation(project(":MainScreen"))
    implementation(project(":SettingsScreen"))
    implementation(project(":PackageInfoProvider"))
    implementation(project(":EventListScreen"))
    implementation(project(":TelegramSetupScreen"))
    implementation(project(":CryptoManager"))
    implementation(project(":Database"))
    implementation(project(":EventDeviceTracker"))
    implementation(project(":EventDetailsScreen"))
    implementation(project(":SelectTrackedAppScreen"))
    implementation(project(":SetupAppPasswordScreen"))
    implementation(project(":EnterPasswordScreen"))
    implementation(project(":BootReceiver"))

    ksp(libs.dagger.compiler)

    implementation(libs.datastore)
    implementation(libs.appcompat)
    implementation(libs.appcompat.resources)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)

    coreLibraryDesugaring(libs.desugar.jdk.libs)


    //Instrumental Test
    androidTestImplementation(libs.instrumental.espresso)
    androidTestImplementation(libs.instrumental.test.runner)
    androidTestImplementation(libs.instrumental.test.core)
    androidTestImplementation(libs.instrumental.junit.ktx)
    androidTestImplementation(libs.instrumental.test.rules)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.agent)

    //Test
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.testng)
}
