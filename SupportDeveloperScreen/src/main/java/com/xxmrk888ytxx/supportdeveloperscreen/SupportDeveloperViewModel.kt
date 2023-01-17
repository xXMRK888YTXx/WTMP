package com.xxmrk888ytxx.supportdeveloperscreen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleRegister
import com.xxmrk888ytxx.coredeps.SharedInterfaces.BillingManager
import com.xxmrk888ytxx.coredeps.ifNotNull
import javax.inject.Inject

class SupportDeveloperViewModel @Inject constructor(
    private val billingManager: BillingManager
) : ViewModel(),ActivityLifecycleCallback {

    override fun onRegister(activity: Activity) {
        super.onRegister(activity)
        this.activity = activity
    }

    override fun onCreate(activity: Activity) {
        super.onCreate(activity)
        this.activity = activity
    }

    override fun onDestroy() {
        super.onDestroy()
        activity = null
    }

    @SuppressLint("StaticFieldLeak")
    private var activity:Activity? = null

    private var activityLifecycleRegister:ActivityLifecycleRegister? = null

    fun registerActivityCallBack(activityLifecycleRegister: ActivityLifecycleRegister) {
        this.activityLifecycleRegister = activityLifecycleRegister
        activityLifecycleRegister.registerCallback(this)
    }

    override fun onCleared() {
        activityLifecycleRegister?.unregisterCallback(this)
        activityLifecycleRegister = null
        activity = null
        super.onCleared()
    }

    fun buyDeveloperSupportOn5Dollars() {
        activity.ifNotNull {
            billingManager.buyDeveloperSupportOn5Dollars(this)
        }
    }

    fun buyDeveloperSupportOn10Dollars() {
        activity.ifNotNull {
            billingManager.buyDeveloperSupportOn10Dollars(this)
        }
    }

    fun buyDeveloperSupportOn15Dollars() {
        activity.ifNotNull {
            billingManager.buyDeveloperSupportOn15Dollars(this)
        }
    }

}