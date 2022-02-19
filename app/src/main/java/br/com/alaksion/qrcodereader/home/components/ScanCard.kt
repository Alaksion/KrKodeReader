package br.com.alaksion.qrcodereader.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.unit.sp
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.core_ui.theme.DarkGrey
import br.com.alaksion.core_ui.theme.Orange
import br.com.alaksion.core_ui.theme.QrCodeReaderTheme
import br.com.alaksion.qrcodereader.R

@Composable
fun ScanCard(
    modifier: Modifier = Modifier,
    scan: Scan,
    cardColor: Color = Orange.copy(alpha = 0f),
    onCardClick: (Int) -> Unit,
    onDeleteClick: (Scan) -> Unit
) {
    val dimensions = LocalDimesions.current
    val clipManager = LocalClipboardManager.current

    fun copyToClipboard(value: String) {
        clipManager.setText(AnnotatedString(value))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(cardColor)
            .clickable {
                onCardClick(scan.id)
            }
            .padding(all = dimensions.Padding.small),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = scan.title,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(dimensions.Separators.small))
                Text(
                    text = scan.code,
                    style = MaterialTheme.typography.body2.copy(
                        color = DarkGrey
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(dimensions.Separators.small))
                Text(
                    text = scan.createdAt,
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkGrey
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onDeleteClick(scan) }) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        tint = DarkGrey
                    )
                }
                IconButton(onClick = { copyToClipboard(scan.code) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_copy),
                        contentDescription = null,
                        tint = DarkGrey,
                    )
                }
            }
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
                    onDeleteClick = {}
                )
            }
        }
    }

}