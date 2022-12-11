package MutliUse

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import theme.openSansFont
import theme.primaryFontColor
import theme.snackbarColor

@Composable
fun CustomSnackBar(
    message: String,
    isRtl: Boolean = true,
    textToIcon: Map<String, @Composable () -> Unit>? = null,
    defaultIcon: @Composable (() -> Unit)? = null,
) {
    val icon = textToIcon?.get(message) ?: defaultIcon
    Snackbar(
        backgroundColor = snackbarColor
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            if(icon != null) {
                icon.invoke()
                LazySpacer(width = 15)
            }

            Text(
                text = message,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                fontFamily = openSansFont,
                color = primaryFontColor,
            )
        }
    }
}