package com.xxmrk888ytxx.telegramsetupscreen.models

/**
 * [Ru]
 * Модель соответствует состояниям экрана
 */
/**
 * [En]
 *  Model according to screen states
 */
internal sealed class ScreenState {
    /**
     * [Ru]
     * При этом состоянии показывается форма ввода данных для бота Telegram
     */
    /**
     * [En]
     * In this state, the data entry form for the Telegram bot is shown
     */
    object ChangeTelegramConfigState : ScreenState()

    /**
     * [Ru]
     * Состояние в котором данные для бота сохранены.
     */
    /**
     * [En]
     * The state in which the data for the bot is saved.
     */
    object ConfigSavedState : ScreenState()

    /**
     * [Ru]
     * Состояние в котором идет проверка наличия данных для бота
     * В этого время показывается индикатор загрузки
     */
    /**
     * [En]
     * The state in which the data availability for the bot is checked
     * At this time, the loading indicator is shown
     */
    object LoadConfigState : ScreenState()
}
