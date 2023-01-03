
object Project {
    const val App = ":app"
    const val AdminReceiver = ":AdminReceiver"
    const val Camera = ":Camera"
    const val UserActivityStats = ":UserActivityStats"
    const val ApiTelegram = ":Api-Telegram"
    const val Workers = ":Workers"
    const val MainScreen = ":MainScreen"
    const val SettingsScreen = ":SettingsScreen"
    const val PackageInfoProvider = ":PackageInfoProvider"
    const val EventListScreen = ":EventListScreen"
    const val TelegramSetupScreen = ":TelegramSetupScreen"
    const val CryptoManager = ":CryptoManager"
    const val Database = ":Database"
    const val EventDeviceTracker = ":EventDeviceTracker"
    const val EventDetailsScreen = ":EventDetailsScreen"
    const val SelectTrackedAppScreen = ":SelectTrackedAppScreen"
    const val SetupAppPasswordScreen = ":SetupAppPasswordScreen"
    const val EnterPasswordScreen = ":EnterPasswordScreen"
    sealed class core(val route:String) {
        object core_Android : core(":core:core-Android")
        object core_Compose : core(":core:core-Compose")
    }
}