package MutliUse

import SharedInterfaces.Navigator
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.xxmrk888ytxx.core_compose.R
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import theme.openSansFont
import theme.primaryFontColor
import theme.timeTextColor

/**
 * [Ru]
 * Данная функция создаёт из модели [DeviceEvent.AppOpen] карточку с информацией
 * о собитии получаемого из модели
 */

/**
 * [En]
 * This function creates a card with information from the [DeviceEvent.AppOpen] model
 * about the event received from the model
 */

@Composable
@MustBeLocalization
fun AppOpenItem(
    item: DeviceEvent.AppOpen,
    navigator: Navigator,
    onDelete:() -> Unit
) {

    BaseEventCard(
        colorLine = Color.Cyan,
        onClick = {
            navigator.toEventDetailsScreen(item.eventId)
        },
        onDelete = onDelete
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            LazySpacer(width = 5)

            AsyncImage(
                model = item.icon ?: R.drawable.default_icon,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(30.dp)
            )

            LazySpacer(width = 10)

            Column() {

                Text(
                    text = stringResource(R.string.Application_launch),
                    fontFamily = openSansFont,
                    fontSize = 16.sp,
                    color = primaryFontColor,
                    fontWeight = FontWeight.W500
                )

                Text(
                    text = item.appName ?: item.packageName,
                    fontFamily = openSansFont,
                    fontSize = 16.sp,
                    color = timeTextColor,
                    fontWeight = FontWeight.W800
                )

            }
        }

        LazySpacer(10)

        TimeText(item.time)
    }
}