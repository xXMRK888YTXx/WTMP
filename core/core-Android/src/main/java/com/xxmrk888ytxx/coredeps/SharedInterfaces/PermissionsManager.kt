package com.xxmrk888ytxx.coredeps.SharedInterfaces

import android.app.Activity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

interface PermissionsManager {
    @OptIn(ExperimentalPermissionsApi::class)
    fun requestRuntimePermission(permission: PermissionState)

    fun requestAccessibilityPermissions()

    fun requestAdminPermissions(activity: Activity)

    fun isCameraPermissionGranted() : Boolean

    fun isAccessibilityPermissionGranted() : Boolean

    fun isAdminPermissionGranted() : Boolean

    fun isNotificationPermissionGranted() : Boolean


}