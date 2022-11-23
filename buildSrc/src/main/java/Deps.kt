

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
        const val CoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1"
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
}