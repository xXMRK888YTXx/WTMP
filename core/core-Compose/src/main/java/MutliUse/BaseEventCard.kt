package MutliUse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import theme.cardColor

/**
 * [Ru]
 * Данная функция создаёт базовую макет карты для функций отображающих события устройсва
 * @param colorLine Переданный цвет будет использоваться для окраски линии которая
 * находится в начале данного макета
 *
 * @param content - внутреннее содержимое данного макета
 */

/**
 * [Ru]
 * This function creates a basic map layout for device event display functions
 * @param colorLine The passed color will be used to color the line that
 * is at the beginning of this layout
 *
 * @param content - internal content of this layout
 */
@Composable
fun BaseEventCard(colorLine: Color, content:@Composable ColumnScope.() -> Unit) {
    Card(
        backgroundColor = cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
                top = 10.dp,
                bottom = 10.dp
            )
            .heightIn(min = 100.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            //LazySpacer(width = 10)
            Spacer(Modifier
                .height(60.dp)
                .width(3.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(colorLine)
            )
            LazySpacer(width = 10)
            Column() {
                content(this)
            }

        }

    }
}