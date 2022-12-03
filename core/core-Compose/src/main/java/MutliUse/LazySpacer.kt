package MutliUse

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LazySpacer(height:Int = 1,width:Int = 1) {
    Spacer(Modifier.height(height.dp).width(width.dp))
}