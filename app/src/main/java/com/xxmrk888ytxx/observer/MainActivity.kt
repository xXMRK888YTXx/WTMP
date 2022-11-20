package com.xxmrk888ytxx.observer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.xxmrk888ytxx.coredeps.getApplicationIconByPackageName
import com.xxmrk888ytxx.coredeps.getApplicationNameByPackageName

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bitmap = this@MainActivity.getApplicationIconByPackageName("com.xxmrk888ytxx.privatenote")
            .toBitmap().asImageBitmap()
        setContent {
            Column {
                Image(bitmap = bitmap,
                    contentDescription = "")
                Text(text = this@MainActivity.getApplicationNameByPackageName("com.xxmrk888ytxx.privatenote")!!)
            }

        }
    }
}