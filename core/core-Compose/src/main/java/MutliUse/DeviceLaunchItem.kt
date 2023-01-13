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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.core_compose.R
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import theme.openSansFont
import theme.primaryFontColor

/**
 * [Ru]
 * Данная функция создаёт из модели [DeviceEvent.DeviceLaunch] карточку с информацией
 * о собитии получаемого из модели
 */

/**
 * [En]
 * This function creates a card with information from the [DeviceEvent.DeviceLaunch] model
 * about the event received from the model
 */
@Composable
fun DeviceLaunchItem(
    event: DeviceEvent.DeviceLaunch,
    navigator: Navigator,
    onDeleteEvent: () -> Unit
) {
    BaseEventCard(
        Color.Magenta.copy(0.9f),
        onClick = {
            navigator.toEventDetailsScreen(event.eventId)
        },
        onDelete = onDeleteEvent
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                painter = painterResource(R.drawable.ic_off),
                modifier = Modifier.size(30.dp),
                contentDescription = null,
                tint = primaryFontColor
            )

            LazySpacer(width = 5)

            Text(
                text = stringResource(R.string.Device_loaded),
                fontFamily = openSansFont,
                fontSize = 16.sp,
                color = primaryFontColor,
                fontWeight = FontWeight.W500
            )
        }

        LazySpacer(10)
        TimeText(event.time)
    }
}