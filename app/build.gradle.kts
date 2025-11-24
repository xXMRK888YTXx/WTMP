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


    defaultConfig {
        applicationId = libs.versions.packageName.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()
        versionCode = 20
        versionName = "1.2.2r"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = libs.versions.isR8ProGuardEnableForRelease.get().toBoolean()
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            testProguardFile("test-proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = libs.versions.isR8ProGuardEnableForDebug.get().toBoolean()
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
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
    implementation(project(Project.core.core_Compose.route))
    implementation(project(Project.AdminReceiver))
    implementation(project(Project.Camera))
    implementation(project(Project.ApiTelegram))
    implementation(project(Project.Workers))
    implementation(project(Project.MainScreen))
    implementation(project(Project.SettingsScreen))
    implementation(project(Project.PackageInfoProvider))
    implementation(project(Project.EventListScreen))
    implementation(project(Project.TelegramSetupScreen))
    implementation(project(Project.CryptoManager))
    implementation(project(Project.Database))
    implementation(project(Project.EventDeviceTracker))
    implementation(project(Project.EventDetailsScreen))
    implementation(project(Project.SelectTrackedAppScreen))
    implementation(project(Project.SetupAppPasswordScreen))
    implementation(project(Project.EnterPasswordScreen))
    implementation(project(Project.BootReceiver))

    ksp(libs.dagger.compiler)

    implementation(libs.datastore)
    implementation(libs.appcompat)
    implementation(libs.appcompat.resources)
    implementation(libs.billing)
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
