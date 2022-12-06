package MutliUse

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import com.xxmrk888ytxx.core_compose.R
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import theme.openSansFont
import theme.primaryFontColor

@Composable
@MustBeLocalization
fun AttemptUnlockDeviceItem(item: DeviceEvent.AttemptUnlockDevice) {

    val itemText = if(item is DeviceEvent.AttemptUnlockDevice.Failed) "Введён не верный пароль"
    else "Устройство разблокировано"

    val icon = if(item is DeviceEvent.AttemptUnlockDevice.Failed) R.drawable.ic_phone_lock
    else R.drawable.ic_lock_open

    BaseEventCard(Color.Green) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                painter = painterResource(icon),
                modifier = Modifier.size(30.dp),
                contentDescription = null,
                tint = primaryFontColor
            )

            LazySpacer(width = 5)

            Text(
                text = itemText,
                fontFamily = openSansFont,
                fontSize = 16.sp,
                color = primaryFontColor,
                fontWeight = FontWeight.W500
            )
        }

        LazySpacer(10)
        TimeText(item.time)
    }
}
