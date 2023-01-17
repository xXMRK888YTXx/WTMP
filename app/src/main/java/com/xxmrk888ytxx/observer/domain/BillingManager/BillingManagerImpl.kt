package com.xxmrk888ytxx.observer.domain.BillingManager

import android.content.Context
import com.xxmrk888ytxx.coredeps.SharedInterfaces.BillingManager
import javax.inject.Inject

internal class BillingManagerImpl @Inject constructor(
    private val context: Context
) : BillingManager {
    override fun buyDeveloperSupportOn5Dollars() {
        TODO("Not yet implemented")
    }

    override fun buyDeveloperSupportOn10Dollars() {
        TODO("Not yet implemented")
    }

    override fun buyDeveloperSupportOn15Dollars() {
        TODO("Not yet implemented")
    }
}