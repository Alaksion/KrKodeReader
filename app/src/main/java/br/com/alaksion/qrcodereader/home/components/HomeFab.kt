package br.com.alaksion.qrcodereader.home.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun HomeFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }

}