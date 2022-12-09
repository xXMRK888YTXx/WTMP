package com.xxmrk888ytxx.settingsscreen.models

/**
 * [Ru]
 * Данный класс предстовляет модель, которая используется для установки закруглений
 * параметров настроек
 */
/**
 * [En]
 * This class represents the model that is used to set roundings
 * settings options
 */
internal sealed class SettingsParamShape {
    /**
     * [Ru]
     * Данный обьект означает что необходимо, закруглить всё края.
     * Необходимо использовать только если,в категории только 1 видимимый элемент
     */
    /**
     * [En]
     * This object means that it is necessary to round all the edges.
     * Must be used only if there is only 1 visible element in the category
     */
    object AllShape : SettingsParamShape()

    /**
     * [Ru]
     * Данный обьект означает что необходимо, закруглить только верхнии края.
     * Необходимо использовать только для первого элемента в списке, только если не единственный
     * видемый элемент в списке
     */
    /**
     * [En]
     * This object means that it is necessary to round only the top edges.
     * Must only be used for the first element in the list, unless it is the only one
     * visible element in the list
     */
    object TopShape : SettingsParamShape()

    /**
     * [Ru]
     * Данный обьект означает что необходимо, закруглить только нижние края.
     * Необходимо использовать только для последнего элемента в списке, только если не единственный
     * видемый элемент в списке
     */
    /**
     * [En]
     * This object means that it is necessary to round only the bottom edges.
     * Must only be used for the last element in the list, unless it is the only one
     * visible element in the list
     */
    object BottomShape : SettingsParamShape()

    /**
     * [Ru]
     * Данный обьект означает что не нужно закруглять углы
     * Необходимо использовать если подподаёт под условия других вышеуказанных параметров
     */
    /**
     * [En]
     * This object means that you do not need to round the corners
     * Must be used if subject to the conditions of the other parameters above
     */
    object None : SettingsParamShape()
}