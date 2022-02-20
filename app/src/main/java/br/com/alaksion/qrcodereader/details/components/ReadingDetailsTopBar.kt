package br.com.alaksion.qrcodereader.details.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun ReadingDetailsTopBar(
    modifier: Modifier = Modifier,
    onBackArrowClick: () -> Unit
) {
    TopAppBar(modifier = modifier) {
        IconButton(onClick = onBackArrowClick) {
            Icon(imageVector = Icons.Default.ArrowBack, null)
        }
    }
}