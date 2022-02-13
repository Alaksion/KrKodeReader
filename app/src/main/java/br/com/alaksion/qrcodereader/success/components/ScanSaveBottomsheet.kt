package br.com.alaksion.qrcodereader.success.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import br.com.alaksion.core_ui.providers.LocalDimesions
import br.com.alaksion.qrcodereader.R
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ScanSaveBottomSheet(
    modifier: Modifier = Modifier,
    onChangeScanTitle: (String) -> Unit,
    onClickSave: () -> Unit,
    scanTitle: String,
    isSaveButtonEnabled: Boolean,
    sheetState: ModalBottomSheetState,
) {
    val scope = rememberCoroutineScope()
    val dimensions = LocalDimesions.current

    Column(
        modifier = modifier
    ) {
        IconButton(
            onClick = { scope.launch { sheetState.hide() } },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
        OutlinedTextField(
            label = { Text(stringResource(id = R.string.save_scan_bottomsheet_textfield_label)) },
            value = scanTitle,
            onValueChange = onChangeScanTitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.paddingMedium)
        )
        Spacer(modifier = Modifier.height(dimensions.largeSeparator))
        Button(
            onClick = {
                scope.launch {
                    sheetState.hide()
                    onClickSave()
                }
            },
            enabled = isSaveButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensions.bottomSheetButton),
            shape = RectangleShape
        ) {
            Text(stringResource(id = R.string.save_reading))
        }
    }
}