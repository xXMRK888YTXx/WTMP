package MutliUse

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.toTimeString
import theme.openSansFont
import theme.timeTextColor

/**
 * [Ru]
 * Из переданного ему числа [Long], создаёт текст из передоного
 * времени. Использует функцию расшерения [Long.toTimeString]
 */

/**
 * [En]
 * From the number [Long] passed to it, creates text from the front
 * time. Uses the [Long.toTimeString] extension function
 */
@Composable
fun TimeText(time:Long) {
    Text(
        text = time.toTimeString(),
        fontFamily = openSansFont,
        fontSize = 13.sp,
        color = timeTextColor,
        maxLines = 1,
        fontWeight = FontWeight.W300
    )
}