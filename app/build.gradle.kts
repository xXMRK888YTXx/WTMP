plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id (Deps.Dagger.DaggerKaptPlugin)
}

android {
    namespace = Config.packageName
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSdk
        targetSdk = Config.compileSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =  "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = Config.isR8ProGuardEnableForRelease
            this.proguardFiles("proguard-android-optimize.txt","proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.Compose.KotlinCompiler
    }
    packagingOptions {
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

    kapt (Deps.Dagger.DaggerKaptCompiler)

    implementation(Deps.DataStore.dataStore)
    implementation(Deps.AppCompat.appCompat)

    //Instrumental Test
    androidTestImplementation (Deps.InstrumentalTest.espresso)
    androidTestImplementation (Deps.InstrumentalTest.testRunner)
    androidTestImplementation (Deps.InstrumentalTest.testCore)
    androidTestImplementation (Deps.InstrumentalTest.jUnit)
    androidTestImplementation (Deps.InstrumentalTest.testRules)
    androidTestImplementation(Deps.TestAndroid.MockkAndroid)
    androidTestImplementation(Deps.TestAndroid.MockkAgent)
}

