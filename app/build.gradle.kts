plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.guardsquare)
}

android {
    namespace = Config.packageName
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSdk
        targetSdk = Config.compileSdk
        versionCode = 20
        versionName = "1.2.2r"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = Config.isR8ProGuardEnableForRelease
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            testProguardFile("test-proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = Config.isR8ProGuardEnableForDebug
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.get()
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
    implementation(project(Project.AdUtils))
    implementation(project(Project.SupportDeveloperScreen))

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
