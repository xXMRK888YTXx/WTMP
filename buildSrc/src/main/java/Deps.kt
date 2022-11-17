

object Deps {
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
        const val AndroidJUnit = "androidx.test.ext:junit:1.1.4"
        const val Espresso = "androidx.test.espresso:espresso-core:3.5.0"
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
}