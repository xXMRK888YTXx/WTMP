package com.xxmrk888ytxx.openapptracker

@Suppress("DataClassPrivateConstructor")
data class OpenAppTrackerParams private constructor(
    internal val isActivityOnly:Boolean,
    internal val ignoreList: List<String>
) {
    class Builder() {
        private var isActivityOnly:Boolean = true

        private var ignoreList:List<String> = emptyList()

        fun build() : OpenAppTrackerParams = OpenAppTrackerParams(isActivityOnly,ignoreList)

        fun isActivityOnly(state:Boolean) : Builder {
            isActivityOnly = state
            return this
        }

        fun setIgnoreList(ignorePackages: List<String>) : Builder {
            ignoreList = ignorePackages
            return this
        }

        fun setIgnoreList(vararg ignorePackages:String) : Builder {
            setIgnoreList(ignorePackages.toList())
            return this
        }
    }
}
