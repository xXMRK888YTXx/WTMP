package com.xxmrk888ytxx.observer.domain.PermissionsManager

import android.Manifest
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.admin.DevicePolicyManager
import android.app.admin.DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PermissionsManager
import javax.inject.Inject

class PermissionsManagerImpl @Inject constructor(
    private val context: Context
) : PermissionsManager {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun requestRuntimePermission(permission: PermissionState) {
        permission.launchPermissionRequest()
    }

    override fun requestAccessibilityPermissions() {
       context.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun requestAdminPermissions() {
        context.startActivity(Intent(ACTION_ADD_DEVICE_ADMIN).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
    }

    override fun isAccessibilityPermissionGranted(): Boolean {
        var isAccessibilityEnabled = false
        val accessibilityManager = context.getSystemService(Context.ACCESSIBILITY_SERVICE)
                as? AccessibilityManager ?: return false
        (accessibilityManager).apply {
            installedAccessibilityServiceList.forEach { installedService ->
                installedService.resolveInfo.serviceInfo.apply {
                    if (getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
                            .any { it.resolveInfo.serviceInfo.packageName == packageName
                                    && it.resolveInfo.serviceInfo.name == name
                                    && permission == Manifest.permission.BIND_ACCESSIBILITY_SERVICE
                                    && it.resolveInfo.serviceInfo.packageName == context.packageName })
                        isAccessibilityEnabled = true
                }
            }
        }
        return isAccessibilityEnabled
    }

    override fun isAdminPermissionGranted(): Boolean {
        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as? DevicePolicyManager
        val adminList = (dpm?.activeAdmins ?: emptyList()).map {
            it.packageName
        }

        return context.packageName in adminList
    }
}