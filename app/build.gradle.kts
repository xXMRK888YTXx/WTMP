plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id (Deps.Dagger.DaggerKaptPlugin)
    id ("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
    id ("com.guardsquare.appsweep") version("latest.release")
}

android {
    namespace = Config.packageName
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSdk
        targetSdk = Config.compileSdk
        versionCode = 19
        versionName = "1.2.1r"

        testInstrumentationRunner =  "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = Config.isR8ProGuardEnableForRelease
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro")
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
        sourceCompatibility =  Config.sourceCompatibility
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

    kapt (Deps.Dagger.DaggerKaptCompiler)

    implementation(Deps.DataStore.dataStore)
    implementation(Deps.AppCompat.appCompat)
    implementation(Deps.AppCompat.appCompatRes)
    implementation(Deps.Billing.billing)
    implementation(platform("com.google.firebase:firebase-bom:31.1.1"))
    implementation ("com.google.firebase:firebase-crashlytics-ktx")
    implementation ("com.google.firebase:firebase-analytics-ktx")

    coreLibraryDesugaring(Desugar.time)


    //Instrumental Test
    androidTestImplementation (Deps.InstrumentalTest.espresso)
    androidTestImplementation (Deps.InstrumentalTest.testRunner)
    androidTestImplementation (Deps.InstrumentalTest.testCore)
    androidTestImplementation (Deps.InstrumentalTest.jUnit)
    androidTestImplementation (Deps.InstrumentalTest.testRules)
    androidTestImplementation(Deps.TestAndroid.MockkAndroid)
    androidTestImplementation(Deps.TestAndroid.MockkAgent)

    //Test
    testImplementation(Deps.TestAndroid.MockkAndroid)
    testImplementation(Deps.TestAndroid.MockkAgent)
    testImplementation(Deps.Test.Testing)
}

