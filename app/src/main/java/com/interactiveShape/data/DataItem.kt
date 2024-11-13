package com.interactiveShape.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

data class DataItem(
    val id: Int,
    val color: Color,
    val shapeType: Shape,
    var position: Offset = Offset(100f, 100f),
    var rotation: Float = 0f,
    var scale: Float = 1f,
    var size: Float = 100f,
    var isDragged: Boolean = false,

)