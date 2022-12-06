package MutliUse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

@Composable
@MustBeLocalization
fun AppOpenItem(item: DeviceEvent.AppOpen) {

    BaseEventCard(colorLine = Color.Cyan) {

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
                    text = "Запуск приложения",
                    fontFamily = openSansFont,
                    fontSize = 16.sp,
                    color = primaryFontColor,
                    fontWeight = FontWeight.W500
                )

                Text(
                    text = item.appName ?: "Ошибка получения имени",
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