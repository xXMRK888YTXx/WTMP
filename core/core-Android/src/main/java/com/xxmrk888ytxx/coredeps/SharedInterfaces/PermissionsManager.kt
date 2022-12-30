package com.xxmrk888ytxx.coredeps.SharedInterfaces

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

interface PermissionsManager {
    @OptIn(ExperimentalPermissionsApi::class)
    fun requestRuntimePermission(permission: PermissionState)

    fun requestAccessibilityPermissions()

    fun requestAdminPermissions()

    fun isCameraPermissionGranted() : Boolean

    fun isAccessibilityPermissionGranted() : Boolean

    fun isAdminPermissionGranted() : Boolean


}