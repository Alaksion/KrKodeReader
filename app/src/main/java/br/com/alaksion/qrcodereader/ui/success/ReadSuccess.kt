package br.com.alaksion.qrcodereader.ui.success

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import br.com.alaksion.qrcodereader.R
import br.com.alaksion.qrcodereader.ui.success.components.ScanResultText
import br.com.alaksion.qrcodereader.ui.theme.QrCodeReaderTheme
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = "/success"
)
@Composable
fun ReadSuccess(
    id: Int,
    code: String
) {
    val scrollState = rememberScrollState()
    val clipManager = LocalClipboardManager.current

    fun copyToClipboard(value: String) {
        clipManager.setText(AnnotatedString(value))
    }

    Scaffold {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            val (content, button) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(content) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(button.top)
                        height = Dimension.fillToConstraints
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_outline),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.read_success),
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.read_success_description),
                    style = MaterialTheme.typography.body1.copy(
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                ScanResultText(
                    scrollState = scrollState,
                    text = code,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    onCopyToClickBoard = { copyToClipboard(it) }
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .height(48.dp)
                    .constrainAs(button) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Text("Save reading")
            }
        }
    }
}

@Composable
@Preview
fun ReadSuccessPreview() {
    QrCodeReaderTheme() {
        ReadSuccess(1, "example code resolution")
    }
}