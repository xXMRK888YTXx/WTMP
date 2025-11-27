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
rootProject.name = "WTMP"
include (":app")
include(":core")
include(":core:core-Android")
include(":core:core-Compose")
include(":AdminReceiver")
include(":Camera")
include(":Api-Telegram")
include(":Workers")
include(":MainScreen")
include(":SettingsScreen")
include(":PackageInfoProvider")
include(":EventListScreen")
include(":TelegramSetupScreen")
include(":CryptoManager")
include(":Database")
include(":EventDeviceTracker")
include(":EventDetailsScreen")
include(":SelectTrackedAppScreen")
include(":SetupAppPasswordScreen")
include(":EnterPasswordScreen")
include(":BootReceiver")