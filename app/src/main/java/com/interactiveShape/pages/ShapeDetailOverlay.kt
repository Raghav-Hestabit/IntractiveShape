package com.interactiveShape.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ShapeDetailOverlay(offset: Offset, rotation: Float, scale: Float) {
    Column(
        modifier = Modifier
            .offset(y = (-10).dp)
            .background(Color(0x88FFFFFF))
            .padding(4.dp)
            .zIndex(2f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val shapeDetail = "X: ${offset.x.toInt()}, Y: ${offset.y.toInt()}\n" +
                "Rotation: ${rotation.toInt()}° \n" +
                "Scale: ${String.format("%.2f", scale)}"

        Text(
            text = shapeDetail,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}
