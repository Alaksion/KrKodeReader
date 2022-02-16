package br.com.alaksion.core_ui.providers.dimensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

object Dimesions {

    val Padding = Padding()
    val Separators = Separators()

    //Essential
    val accessibilityHeight = 48.dp
    val bottomSheetButton = 64.dp

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