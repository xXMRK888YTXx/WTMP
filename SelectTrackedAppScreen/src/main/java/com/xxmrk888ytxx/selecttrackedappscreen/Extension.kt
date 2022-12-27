package com.xxmrk888ytxx.selecttrackedappscreen

import com.xxmrk888ytxx.coredeps.models.AppInfo

internal fun List<AppInfo>.filterSearch(searchLine:String) : List<AppInfo> {
    if(searchLine == "") return this
    return this.filter { searchLine.lowercase() in it.appName.lowercase() }
}