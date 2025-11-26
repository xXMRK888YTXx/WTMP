package MutliUse

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.cardColor
import theme.checkedSettingsSwitch
import theme.openSansFont
import theme.primaryFontColor

@Composable
fun StyleButton(
    text:String,
    modifier: Modifier = Modifier,
    isEnable:Boolean = true,
    backgroundColor: Color = cardColor,
    disabledBackgroundColor: Color = backgroundColor.copy(0.5f),
    onClick:() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledBackgroundColor
        ),
        enabled = isEnable,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontFamily = openSansFont,
            maxLines = 1,
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            color = if(isEnable) primaryFontColor else primaryFontColor.copy(0.5f),
            modifier = Modifier.basicMarquee(Int.MAX_VALUE)
        )
    }
}