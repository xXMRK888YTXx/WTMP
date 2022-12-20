package com.xxmrk888ytxx.eventdevicetracker

@Suppress("DataClassPrivateConstructor")
data class EventDeviceTrackerParams private constructor(
    internal val isActivityOnly:Boolean,
    internal val ignoreList: List<String>
) {
    class Builder() {
        private var isActivityOnly:Boolean = true

        private var ignoreList:List<String> = emptyList()

        fun build() : EventDeviceTrackerParams = EventDeviceTrackerParams(isActivityOnly,ignoreList)

        /**
         * [Ru]
         * Параметр указывающий, что будут отслеживаться процессы, которые имеют activity
         */
        /**
         * [En]
         * Parameter indicating that processes that have activity will be monitored
         */
        fun isActivityOnly(state:Boolean) : Builder {
            isActivityOnly = state
            return this
        }

        /**
         * [Ru]
         * Устанавливает имя пакетов которые будут игнорироваться, при отслежевании
         */
        /**
         * [En]
         * Sets the name of the packages to be ignored when tracking
         */
        fun setIgnoreList(ignorePackages: List<String>) : Builder {
            ignoreList = ignorePackages
            return this
        }

        /**
         * [Ru]
         * Устанавливает имя пакетов которые будут игнорироваться, при отслежевании
         */
        /**
         * [En]
         * Sets the name of the packages to be ignored when tracking
         */
        fun setIgnoreList(vararg ignorePackages:String) : Builder {
            setIgnoreList(ignorePackages.toList())
            return this
        }
    }
}
