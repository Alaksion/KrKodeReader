package br.com.alaksion.qrcodereader.reader.ui.success.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alaksion.qrcodereader.R
import br.com.alaksion.core_ui.theme.DarkGrey
import br.com.alaksion.core_ui.theme.LightGrey
import br.com.alaksion.core_ui.theme.QrCodeReaderTheme

@Composable
fun ScanResultText(
    modifier: Modifier = Modifier,
    text: String,
    onCopyToClickBoard: (String) -> Unit,
    scrollState: ScrollState
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(br.com.alaksion.core_ui.theme.LightGrey)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        IconButton(
            onClick = { onCopyToClickBoard(text) },
            modifier = Modifier.align(Alignment.Top)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = null,
                tint = br.com.alaksion.core_ui.theme.DarkGrey,
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
                color = br.com.alaksion.core_ui.theme.DarkGrey
            )
        )
    }
}

@Preview
@Composable
private fun ScanResultTextPreview() {
    br.com.alaksion.core_ui.theme.QrCodeReaderTheme() {
        Scaffold() {
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