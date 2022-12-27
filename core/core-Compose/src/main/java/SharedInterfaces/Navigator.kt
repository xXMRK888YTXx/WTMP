package SharedInterfaces

/**
 * [Ru]
 * Данный интерфейс предназначен для навигации, он передаётся в compose функиции
 * [En]
 * This interface is intended for navigation, it is passed to the compose function
 */
interface Navigator {

    fun navigateUp()

    fun toSettingsScreen()

    fun toEventListScreen()

    fun toTelegramSetupScreen()

    fun toEventDetailsScreen(eventId:Int)

    fun toSelectTrackedAppScreen()

    companion object {
        const val EventDetailsScreenKey = "EventDetailsScreenKey"
    }
}