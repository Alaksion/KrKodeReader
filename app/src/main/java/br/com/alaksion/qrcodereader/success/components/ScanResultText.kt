package br.com.alaksion.qrcodereader.success.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.core_ui.theme.DarkGrey
import br.com.alaksion.core_ui.theme.LightGrey
import br.com.alaksion.core_ui.theme.QrCodeReaderTheme
import br.com.alaksion.qrcodereader.R

@Composable
internal fun ScanResultText(
    modifier: Modifier = Modifier,
    text: String,
    onCopyToClickBoard: (String) -> Unit,
    scrollState: ScrollState
) {
    val dimension = LocalDimesions.current
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(LightGrey)
            .padding(dimension.Padding.small),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { onCopyToClickBoard(text) },
            modifier = Modifier.align(Alignment.Top)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = null,
                tint = DarkGrey,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold,
                color = DarkGrey
            )
        )
    }
}

@Preview
@Composable
private fun ScanResultTextPreview() {
    QrCodeReaderTheme() {
        Scaffold {
            ScanResultText(
                text = stringResource(id = R.string.lorem_ipsum),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onCopyToClickBoard = {},
                scrollState = rememberScrollState()
            )
        }
    }
}
