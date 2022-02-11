package br.com.alaksion.core_utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatCurrentDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    return sdf.format(this)
}