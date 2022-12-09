package com.xxmrk888ytxx.telegramsetupscreen

import MutliUse.LazySpacer
import SharedInterfaces.Navigator
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import theme.checkedSettingsSwitch
import theme.openSansFont
import theme.primaryFontColor

@Composable
@MustBeLocalization
fun TelegramSetupScreen(telegramViewModel: TelegramViewModel,navigator: Navigator) {
    LazyColumn() {
        item {
            TopBar(navigator)
            LazySpacer(height = 20)
        }

        item {
            val text = remember {
                mutableStateOf("")
            }
            InputInfoTextField(
                value = text.value,
                onValueChanged = {
                    text.value = it
                },
                label = "Id пользователя",
                placeholder = "23543564654"
            )
        }
    }
}

@Composable
internal fun InputInfoTextField(
    value:String,
    onValueChanged:(String) -> Unit,
    label:String,
    placeholder:String
) {
    OutlinedTextField (
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
            singleLine = true,
        label = {
            Text(
                text = label,
                fontFamily = openSansFont,
                fontSize = 17.sp,
                fontWeight = FontWeight.W600,

            )
        },
        placeholder = {
          Text(text = placeholder)
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = primaryFontColor,
            placeholderColor = primaryFontColor.copy(0.6f),
            focusedBorderColor = checkedSettingsSwitch,
            unfocusedBorderColor = primaryFontColor,
            unfocusedLabelColor = primaryFontColor,
            focusedLabelColor = checkedSettingsSwitch
        )
    )
}

@Composable
internal fun TopBar(navigator: Navigator) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = navigator::navigateUp) {
            Icon(
                painter = painterResource(R.drawable.ic_back_arrow),
                contentDescription = "",
                tint = primaryFontColor,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}