package com.xxmrk888ytxx.mainscreen.models

import kotlinx.coroutines.flow.StateFlow

data class RequestedPermission(
    val description:String,
    val permissionState: StateFlow<Boolean>,
    val onRequest:() -> Unit,
)