package br.com.alaksion.qrcodereader.details.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import br.com.alaksion.core_ui.components.Bottomsheet
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.core_ui.theme.DarkGrey
import br.com.alaksion.qrcodereader.R

@Composable
@ExperimentalMaterialApi
internal fun DeleteReadingBottomSheet(
    modifier: Modifier = Modifier,
    dissmissClick: () -> Unit,
    onConfirmDeleteClick: () -> Unit
) {
    val dimensions = LocalDimesions.current

    Bottomsheet(
        onClickClose = dissmissClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.bottom_sheet_title),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                color = DarkGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.Padding.small)
        )
        Spacer(modifier = Modifier.height(dimensions.Separators.medium))
        Text(
            text = stringResource(id = R.string.bottom_sheet_description),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.Padding.small)
        )
        Spacer(modifier = Modifier.height(dimensions.Separators.medium))
        Button(
            onClick = onConfirmDeleteClick,
            modifier = Modifier
                .height(dimensions.accessibilityHeight)
                .padding(horizontal = dimensions.Padding.small)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.bottom_sheet_delete))
        }
        TextButton(
            onClick = dissmissClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.Padding.small)
                .height(dimensions.accessibilityHeight)
        ) {
            Text(stringResource(id = R.string.text_close))
        }
        Spacer(modifier = Modifier.height(dimensions.Separators.small))
    }

}