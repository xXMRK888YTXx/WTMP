package com.xxmrk888ytxx.supportdeveloperscreen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.adutils.AdStateManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback.ActivityLifecycleCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback.ActivityLifecycleRegister
import com.xxmrk888ytxx.coredeps.SharedInterfaces.BillingManager
import com.xxmrk888ytxx.coredeps.ifNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SupportDeveloperViewModel @Inject constructor(
    private val billingManager: BillingManager,
    private val adStateManager: AdStateManager
) : ViewModel(), ActivityLifecycleCallback {

    internal val isNeedShowAd = adStateManager.isNeedShowAd

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

    private var activityLifecycleRegister: ActivityLifecycleRegister? = null

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

    internal fun restorePurchase() {
        viewModelScope.launch(Dispatchers.Default) {
            billingManager.restorePurchases()
        }
    }

}