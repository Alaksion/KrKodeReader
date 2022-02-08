package br.com.alaksion.core_ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColors(
    primary = Orange,
    onPrimary = Color.White,
    primaryVariant = LighOrange,
    background = Color.White,
    onBackground = Black
)

@Composable
fun QrCodeReaderTheme(content: @Composable () -> Unit) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}