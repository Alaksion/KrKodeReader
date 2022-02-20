package br.com.alaksion.qrcodereader.home.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import br.com.alaksion.qrcodereader.R


@Composable
fun HomeFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_qr_scanner), contentDescription = null)
    }

}