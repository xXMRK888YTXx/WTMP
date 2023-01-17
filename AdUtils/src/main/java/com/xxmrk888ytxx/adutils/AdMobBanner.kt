package com.xxmrk888ytxx.adutils

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.xxmrk888ytxx.adutils.models.AdMobKey

@SuppressLint("VisibleForTests")
@Composable
fun AdMobBanner(adMobKey:AdMobKey,background: Color) {
    var view: AdView? = null
    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            view?.destroy()
            view = null
        }
    })
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .background(background),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = if(BuildConfig.DEBUG) AdMobKey.TestBannerKey.key
                    else adMobKey.key
                view = this
                loadAd(AdRequest.Builder().build())
            }

        }
    )
}