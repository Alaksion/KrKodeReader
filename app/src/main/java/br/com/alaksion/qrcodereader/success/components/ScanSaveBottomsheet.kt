package br.com.alaksion.qrcodereader.success.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.core_ui.theme.DarkGrey
import br.com.alaksion.qrcodereader.R
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
internal fun ScanSaveBottomSheet(
    modifier: Modifier = Modifier,
    onChangeScanTitle: (String) -> Unit,
    onClickSave: () -> Unit,
    scanTitle: String,
    isSaveButtonEnabled: Boolean,
    sheetState: ModalBottomSheetState,
) {
    val scope = rememberCoroutineScope()
    val dimensions = LocalDimesions.current
    val focusManager = LocalFocusManager.current

    val dismiss: () -> Unit = remember {
        {
            scope.launch { sheetState.hide() }
        }
    }

    Column(
        modifier = modifier
    ) {
        IconButton(
            onClick = { dismiss() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
        Text(
            text = stringResource(id = R.string.save_scan_bottomsheet_title),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                color = DarkGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.Padding.medium),
        )
        Spacer(modifier = Modifier.height(dimensions.Separators.large))
        OutlinedTextField(
            label = { Text(stringResource(id = R.string.save_scan_bottomsheet_textfield_label)) },
            value = scanTitle,
            onValueChange = onChangeScanTitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.Padding.medium),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.height(dimensions.Separators.large))
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
                .height(dimensions.accessibilityHeight)
                .padding(horizontal = dimensions.Padding.medium),
        ) {
            Text(stringResource(id = R.string.text_save))
        }
        TextButton(
            onClick = { dismiss() },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensions.accessibilityHeight)
                .padding(horizontal = dimensions.Padding.medium)
        ) {
            Text(stringResource(id = R.string.text_close))
        }
        Spacer(modifier = Modifier.height(dimensions.Separators.small))
    }
}