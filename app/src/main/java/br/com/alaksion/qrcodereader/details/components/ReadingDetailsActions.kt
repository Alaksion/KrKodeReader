package br.com.alaksion.qrcodereader.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.qrcodereader.R

@Composable
fun ReadingDetailsActions(
    modifier: Modifier = Modifier,
    onClickCopy: () -> Unit,
    onClickSave: () -> Unit,
    onClickShare: () -> Unit,
    onCLickDelete: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
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
        IconButton(
            onClick = onClickSave,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary),
        ) {
            Icon(
                imageVector = Icons.Outlined.Create,
                contentDescription = null,
                tint = Color.White
            )
        }
        IconButton(
            onClick = onClickShare,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary),
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                tint = Color.White
            )
        }
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