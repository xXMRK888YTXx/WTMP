package MutliUse

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.cardColor
import theme.openSansFont
import theme.primaryFontColor

@Composable
fun StyleButton(text:String,onClick:() -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = cardColor
        )
    ) {
        Text(
            text = text,
            fontFamily = openSansFont,
            maxLines = 1,
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            color = primaryFontColor
        )
    }
}