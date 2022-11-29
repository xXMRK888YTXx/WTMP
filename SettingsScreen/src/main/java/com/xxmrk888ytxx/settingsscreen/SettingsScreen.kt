package com.xxmrk888ytxx.settingsscreen

import MutliUse.LazySpacer
import SharedInterfaces.Navigator
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.MustBeLocalization

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel,navigator: Navigator) {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            TopBar()
        }
    }
}

@Composable
@MustBeLocalization
internal fun TopBar() {
    Row(Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
        LazySpacer(1,5)
        Icon(painter = painterResource(R.drawable.ic_back_arrow),
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.size(25.dp)
        )
        LazySpacer(height = 1, width = 25)
        Text(
            "Настройки",
            fontSize = 25.sp,
            fontWeight = FontWeight.W800,
            color = Color.White
        )
    }
}