package br.com.alaksion.core_ui.providers.activity

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun <T : Activity> GetActivity(): T {
    val context = LocalContext.current
    return context as T
}