package MutliUse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun GradientButton(
    backgroundGradient : Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape:Shape = MaterialTheme.shapes.small,
    buttonContent:@Composable () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .background(backgroundGradient)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            buttonContent()
        }
    }
}