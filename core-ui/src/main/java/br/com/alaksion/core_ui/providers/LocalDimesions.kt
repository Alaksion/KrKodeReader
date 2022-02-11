package br.com.alaksion.core_ui.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

object Dimesions {

    //Essential
    val acessibilityHeight = 48.dp

    // Padding
    val paddingMedium = 20.dp
    val paddingSmall = 10.dp

    //Separators
    val smallSeparator = 5.dp
    val mediumSeparator = 10.dp

}

val LocalDimesions = staticCompositionLocalOf { Dimesions }

@Composable
fun DimesionsProvider(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDimesions provides Dimesions) {
        content()
    }

}