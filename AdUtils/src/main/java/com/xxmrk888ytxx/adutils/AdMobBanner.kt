package com.xxmrk888ytxx.adutils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.xxmrk888ytxx.adutils.models.AdMobKey

@Composable
fun AdMobBanner(adMobKey:AdMobKey,background: Color) {
    AndroidView(
        modifier = Modifier.fillMaxWidth().background(background),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adMobKey.key
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}