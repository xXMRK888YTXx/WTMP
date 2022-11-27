pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Observer"
include (":app")
include(":core")
include(":core:core-Android")
include(":core:core-Compose")
include(":AdminReceiver")
include(":Camera")
include(":UserActivityStats")
include(":Api-Telegram")
include(":Workers")
