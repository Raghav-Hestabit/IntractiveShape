package com.interactiveShape.data

import androidx.compose.ui.geometry.Offset

data class GestureResult(
    val scale: Float,
    val rotation: Float,
    val offset: Offset
)