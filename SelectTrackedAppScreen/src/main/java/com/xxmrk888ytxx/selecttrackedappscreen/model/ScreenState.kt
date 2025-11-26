package com.xxmrk888ytxx.selecttrackedappscreen.model

import com.xxmrk888ytxx.coredeps.models.AppInfo
import org.w3c.dom.Text

internal sealed class ScreenState {
    object Loading : ScreenState()

    data class ShopAppListState(val appList: List<AppInfo> = listOf(), val searchLineText: String = "") :
        ScreenState()
}