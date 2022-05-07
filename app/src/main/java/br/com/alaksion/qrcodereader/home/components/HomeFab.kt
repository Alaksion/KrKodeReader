package br.com.alaksion.qrcodereader.home.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import br.com.alaksion.core_ui.R as coreR

@Composable
fun HomeFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        Icon(
            painter = painterResource(id = coreR.drawable.ic_qr_scanner),
            contentDescription = null
        )
    }

}
