package com.xxmrk888ytxx.selecttrackedappscreen

import com.xxmrk888ytxx.coredeps.models.AppInfo

internal sealed class ScreenState {
    object Loading : ScreenState()

    class ShopAppListState(val appList:List<AppInfo>) : ScreenState()
}