package MutliUse

import SharedInterfaces.Navigator
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

/**
 * [Ru]
 * Данная функция создаёт из модели [DeviceEvent.AttemptUnlockDevice] карточку с информацией
 * о собитии получаемого из модели.
 *
 * Если переданная моделя является [DeviceEvent.AttemptUnlockDevice.Succeeded]
 * то будет создана карточка успешной попытки разблокировки устройтсва
 *
 * Если переданная моделя является [DeviceEvent.AttemptUnlockDevice.Failed]
 * то будет создана карточка не удачной попытки разблокировки устройтсва.
 */

/**
 * [Ru]
 * This function creates a card with information from the [DeviceEvent.AttemptUnlockDevice] model
 * about the event received from the model.
 *
 * If the passed model is [DeviceEvent.AttemptUnlockDevice.Succeeded]
 * then a card of a successful attempt to unlock the device will be created
 *
 * If the passed model is [DeviceEvent.AttemptUnlockDevice.Failed]
 * then a card of an unsuccessful attempt to unlock the device will be created.
 */

@Composable
@MustBeLocalization
fun AttemptUnlockDeviceItem(item: DeviceEvent.AttemptUnlockDevice,navigator: Navigator) {

    val itemText = if(item is DeviceEvent.AttemptUnlockDevice.Failed) "Введён не верный пароль"
    else "Устройство разблокировано"

    val icon = if(item is DeviceEvent.AttemptUnlockDevice.Failed) R.drawable.ic_phone_lock
    else R.drawable.ic_lock_open

    BaseEventCard(
        Color.Green,
        onClick = {
            navigator.toEventDetailsScreen(item.eventId)
        }
    ) {
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
