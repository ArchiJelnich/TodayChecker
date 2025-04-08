package com.example.today.infra

import androidx.compose.ui.graphics.Color


fun Color.toHexString(): String {
    return String.format(
        "#%02X%02X%02X%02X",
        (this.red * 255).toInt(),
        (this.green * 255).toInt(),
        (this.blue * 255).toInt(),
        (this.alpha * 255).toInt()
    )
}

fun String.toColor(): Color {
    val color = this.trimStart('#')
    val r = Integer.valueOf(color.substring(0, 2), 16)
    val g = Integer.valueOf(color.substring(2, 4), 16)
    val b = Integer.valueOf(color.substring(4, 6), 16)
    val a = if (color.length == 8) Integer.valueOf(color.substring(6, 8), 16) else 255 // Default alpha to 255 if not provided

    return Color(r / 255f, g / 255f, b / 255f, a / 255f)
}