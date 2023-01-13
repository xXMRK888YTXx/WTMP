package com.xxmrk888ytxx.adutils.models

sealed class AdMobKey(internal val key:String) {
    object MainScreenBanner : AdMobKey("ca-app-pub-6313040706847570/1068247508")

    object EventListScreenBanner : AdMobKey("ca-app-pub-6313040706847570/4532548479")

    object MainScreenToEventDetailsScreenAd : AdMobKey("ca-app-pub-6313040706847570/4876981649")

    object MainScreenToEventListScreenAd : AdMobKey("ca-app-pub-6313040706847570/5717938950")

    object MainScreenToSettingsScreenAd : AdMobKey("ca-app-pub-6313040706847570/9593303464")
}
