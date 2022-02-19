package br.com.alaksion.core_ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val LightColorPalette = lightColors(
    primary = Orange,
    primaryVariant = LightOrange,
    onPrimary = AppWhite,
    secondary = Blue,
    secondaryVariant = LightBlue,
    onSecondary = AppWhite,
    background = AppWhite,
    onBackground = AppBlack,
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