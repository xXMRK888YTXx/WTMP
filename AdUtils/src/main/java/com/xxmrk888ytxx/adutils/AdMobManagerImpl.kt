package com.xxmrk888ytxx.adutils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.xxmrk888ytxx.adutils.models.AdMobKey
import com.xxmrk888ytxx.coredeps.ApplicationScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdMobManagerImpl @Inject constructor(
    private val context: Context,
    private val adStateManager: AdStateManager
) : AdAppManager {

    override fun initAdmob() {
        MobileAds.initialize(context)
    }

    override fun showMainScreenToEventDetailsScreenInterstitialAd(activity: Activity) {
        val adRequest = AdRequest.Builder().build()
        showInterstitialAd(AdMobKey.MainScreenToEventDetailsScreenAd,adRequest,activity)
    }

    override fun showMainScreenToEventListScreenInterstitialAd(activity: Activity) {
        val adRequest = AdRequest.Builder().build()
        showInterstitialAd(AdMobKey.MainScreenToEventListScreenAd,adRequest, activity)
    }

    override fun showMainScreenToSettingsScreenInterstitialAd(activity: Activity) {
        val adRequest = AdRequest.Builder().build()
        showInterstitialAd(AdMobKey.MainScreenToSettingsScreenAd,adRequest,activity)
    }

    private fun showInterstitialAd(adKey:AdMobKey,adRequest: AdRequest, activity:Activity) {
       ApplicationScope.launch(Dispatchers.IO) {
           if(!adStateManager.isNeedShowAd.first()) return@launch

           withContext(Dispatchers.Main) {
               InterstitialAd.load(
                   context,
                   adKey.key,
                   adRequest,
                   object : InterstitialAdLoadCallback() {
                       override fun onAdFailedToLoad(error: LoadAdError) {
                           super.onAdFailedToLoad(error)
                           Log.d("MyLog","InterstitialAd load error:${error.message}")
                       }

                       override fun onAdLoaded(ad: InterstitialAd) {
                           super.onAdLoaded(ad)
                           ad.show(activity)
                       }
                   }
               )
           }
       }
    }
}