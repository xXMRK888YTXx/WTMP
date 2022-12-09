package MutliUse

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * [Ru]
 * Элемент [Spacer] в котором можно быстро указать его ширину и длинну.
 * Используется как альтернатива [Modifier.padding]
 */
/**
 * [En]
 * The [Spacer] element in which you can quickly specify its width and length.
 * Used as an alternative to [Modifier.padding]
 */
@Composable
fun LazySpacer(height:Int = 1,width:Int = 1) {
    Spacer(Modifier
        .height(height.dp)
        .width(width.dp))
}