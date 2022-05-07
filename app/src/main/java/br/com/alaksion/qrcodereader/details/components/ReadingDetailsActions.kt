package br.com.alaksion.qrcodereader.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import br.com.alaksion.qrcodereader.R

@Composable
fun ReadingDetailsActions(
    modifier: Modifier = Modifier,
    onClickCopy: () -> Unit,
    onCLickDelete: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary),
            onClick = onClickCopy
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = null,
                tint = Color.White,
            )
        }
        Spacer(
            modifier = Modifier.width(
                dimensionResource(id = br.com.alaksion.core_ui.R.dimen.medium)
            )
        )
        IconButton(
            onClick = onCLickDelete,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary),
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
