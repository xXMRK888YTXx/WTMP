package com.xxmrk888ytxx.adutils

import android.content.Context
import com.google.android.gms.ads.MobileAds
import javax.inject.Inject

class AdMobManagerImpl @Inject constructor(
    private val context: Context
) : AdAppManager {

    override fun initAdmob() {
        MobileAds.initialize(context)
    }


}