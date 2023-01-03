

object Deps {
    private const val MockkVersion = "1.13.2"
    object Compose {
        private const val ComposeVersion = "1.3.0-rc01"
        private const val MaterialVersion = "1.3.1"
        const val KotlinCompiler = "1.3.2"
        const val UI = "androidx.compose.ui:ui:$ComposeVersion"
        const val Tooling = "androidx.compose.ui:ui-tooling-preview:$ComposeVersion"
        const val Material = "androidx.compose.material:material:$MaterialVersion"
        const val TestJUnit = "androidx.compose.ui:ui-test-junit4:$ComposeVersion"
        const val TestTooling = "androidx.compose.ui:ui-tooling:$ComposeVersion"
        const val TestManifest = "androidx.compose.ui:ui-test-manifest:$ComposeVersion"
        const val Navigation = "androidx.navigation:navigation-compose:2.5.2"
        const val GoogleFonts =  "androidx.compose.ui:ui-text-google-fonts:$ComposeVersion"
    }
    object Test {
        const val JUnit = "junit:junit:4.13.2"
        const val Mockk = "io.mockk:mockk:$MockkVersion"
        const val Testing = "org.testng:testng:6.9.6"
    }
    object TestAndroid {
        const val AndroidJUnit = "androidx.test.ext:junit:1.1.4"
        const val Espresso = "androidx.test.espresso:espresso-core:3.5.0"
        const val MockkAndroid = "io.mockk:mockk-android:$MockkVersion"
        const val MockkAgent = "io.mockk:mockk-agent:$MockkVersion"

    }
    object AndroidX {
        const val AndroidCore = "androidx.core:core-ktx:1.9.0"
        const val LifeCycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
        const val ComposeActivity =  ("androidx.activity:activity-compose:1.6.1")
    }
    object ViewModel {
        private const val version = "2.5.1"
        const val ViewModel = ("androidx.lifecycle:lifecycle-viewmodel:$version")
        const val ViewModelKotlin =  ("androidx.lifecycle:lifecycle-viewmodel-ktx:$version")
    }
    object Dagger { //https://dagger.dev/
        const val version = "2.44"
        const val DaggerCore = "com.google.dagger:dagger:2.44"
        const val DaggerKaptCompiler = "com.google.dagger:dagger-compiler:2.44"
        const val DaggerKaptPlugin = "kotlin-kapt"
    }
    object Coroutines {
        private const val version = "1.6.1"
        const val CoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        object Test {
            const val CoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }
    object CameraX {
        private const val camerax_version = "1.3.0-alpha01"
        // The following line is optional, as the core library is included indirectly by camera-camera2
        const val CameraCore =  "androidx.camera:camera-core:${camerax_version}"
        const val Camera2 = "androidx.camera:camera-camera2:${camerax_version}"
        const val CameraExtensions = "androidx.camera:camera-extensions:${camerax_version}"
        const val CameraLifecycle = "androidx.camera:camera-lifecycle:${camerax_version}"
    }
    object Retrofit2 {
        private const val version = "2.9.0"
        private const val okHttpVersion = "4.10.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val jsonConverter = "com.squareup.retrofit2:converter-gson:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
        object Test {
            const val MockServer = "com.squareup.okhttp3:mockwebserver:$okHttpVersion"
        }
    }
    object WorkManager {
        private const val version = "2.7.1"
        const val workManager = "androidx.work:work-runtime-ktx:$version"
        const val workManagerTest = "androidx.work:work-testing:$version"
    }
    object InstrumentalTest {
        private const val testVersion = "1.5.0"
        const val espresso = "androidx.test.espresso:espresso-core:3.5.0"
        const val testRunner = "androidx.test:runner:1.5.1"
        const val testCore = "androidx.test:core:$testVersion"
        const val jUnit = "androidx.test.ext:junit-ktx:1.1.4"
        const val testRules = "androidx.test:rules:$testVersion"
    }
    object Coil {
        const val coil = "io.coil-kt:coil-compose:2.2.2"
    }
    object Moshi {
        private const val version = "1.13.0"
        const val moshi = "com.squareup.moshi:moshi:$version"
        const val moshiKaptPlugin = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    }
    object KaptPlugin {
        const val kapt = "kotlin-kapt"
    }
    object DataStore {
        const val dataStore = "androidx.datastore:datastore-preferences:1.0.0"
    }
    object Room {
        private const val version = "2.4.3"
        const val RoomRuntime =  "androidx.room:room-runtime:$version"
        const val KaptCompiler = "androidx.room:room-compiler:$version"
        const val RoomKTX = "androidx.room:room-ktx:$version"
        object Test {
            const val RoomTest = "androidx.room:room-testing:$version"
        }
    }
    object Permissions {
        const val permission = "com.google.accompanist:accompanist-permissions:0.26.2-beta"
    }
    object Biometric {
        const val biometric = "androidx.biometric:biometric-ktx:1.2.0-alpha05"
    }
    object Fragment {
        const val fragment = "androidx.fragment:fragment-ktx:1.5.5"
    }


}