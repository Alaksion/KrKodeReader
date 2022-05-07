package br.com.alaksion.qrcodereader.details.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import br.com.alaksion.qrcodereader.R

@Composable
internal fun ReadingDetailsTopBar(
    modifier: Modifier = Modifier,
    onBackArrowClick: () -> Unit
) {
    TopAppBar(modifier = modifier) {
        IconButton(onClick = onBackArrowClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack, null,
                tint = Color.White
            )
        }
        Text(
            stringResource(id = R.string.scan_details),
            style = MaterialTheme.typography.h6.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
