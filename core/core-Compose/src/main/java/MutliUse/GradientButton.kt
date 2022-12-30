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

/**
 * [Ru]
 * Данная функция создаёт кнобку с градиентом на фоне
 */

/**
 * [En]
 * This function creates a button with a gradient background
 */

@Composable
fun GradientButton(
    backgroundGradient : Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape:Shape = MaterialTheme.shapes.small,
    enabled:Boolean = true,
    buttonContent:@Composable () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
        enabled = enabled,
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