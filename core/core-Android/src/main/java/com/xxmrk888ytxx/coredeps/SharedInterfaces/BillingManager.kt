package com.xxmrk888ytxx.coredeps.SharedInterfaces

import android.app.Activity

interface BillingManager {

    fun connectToGooglePlay()
    fun buyDeveloperSupportOn5Dollars(activity:Activity)

    fun buyDeveloperSupportOn10Dollars(activity:Activity)

    fun buyDeveloperSupportOn15Dollars(activity:Activity)
    fun restorePurchases()
}