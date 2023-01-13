package com.xxmrk888ytxx.adutils

import android.app.Activity

interface AdAppManager {

    fun initAdmob()

    fun showMainScreenToEventDetailsScreenInterstitialAd(activity: Activity)

    fun showMainScreenToEventListScreenInterstitialAd(activity: Activity)

    fun showMainScreenToSettingsScreenInterstitialAd(activity: Activity)
}