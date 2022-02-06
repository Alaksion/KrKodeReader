package br.com.alaksion.qrcodereader.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import br.com.alaksion.qrcodereader.R

class ToastHelper(private val context: Context) {

    fun showToast(@StringRes textId: Int) {
        Toast.makeText(context, context.getText(R.string.copy_to_clipboard), Toast.LENGTH_SHORT)
            .show()
    }

}