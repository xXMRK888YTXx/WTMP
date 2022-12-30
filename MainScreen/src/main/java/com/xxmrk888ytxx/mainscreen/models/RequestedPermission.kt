package com.xxmrk888ytxx.mainscreen.models

import kotlinx.coroutines.flow.Flow

data class RequestedPermission(
    val description:String,
    val permissionState: Flow<Boolean>,
    val onRequest:() -> Unit,
)