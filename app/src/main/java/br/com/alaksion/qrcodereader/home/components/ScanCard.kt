package br.com.alaksion.qrcodereader.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.core_ui.theme.QrCodeReaderTheme
import br.com.alaksion.qrcodereader.R
import br.com.alaksion.core_ui.R as coreR

@Composable
fun ScanCard(
    modifier: Modifier = Modifier,
    scan: Scan,
    cardColor: Color,
    onCardClick: (Long) -> Unit,
) {
    val dimensions = LocalDimesions.current
    val clipManager = LocalClipboardManager.current

    fun copyToClipboard(value: String) {
        clipManager.setText(AnnotatedString(value))
    }

    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(cardColor)
            .clickable {
                onCardClick(scan.id)
            }
            .padding(dimensions.Padding.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensions.Separators.medium)
    ) {
        Icon(
            painter = painterResource(id = coreR.drawable.ic_qrcode),
            contentDescription = null,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(Color.White)
                .padding(dimensions.Padding.small)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = scan.title,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = scan.code,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(onClick = { copyToClipboard(scan.code) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = null
            )
        }
    }

}

@Composable
@Preview
private fun ScanCardPreview() {
    QrCodeReaderTheme {
        Scaffold() {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ScanCard(
                    scan = Scan(1, "01/01/2000", "Title", "CODECODECODECODE"),
                    cardColor = Color.Cyan,
                    onCardClick = {},
                )
            }
        }
    }

}
