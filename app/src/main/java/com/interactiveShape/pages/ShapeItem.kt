package com.interactiveShape.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.interactiveShape.data.DataItem
import com.interactiveShape.viewModel.ShapeViewModel
import kotlin.math.cos
import kotlin.math.sin
import kotlin.text.*

@Composable
fun ShapeItem(
    shape: DataItem,
    remove: () -> Unit
) {

    var offset by remember { mutableStateOf(shape.position) }
    var scale by remember { mutableFloatStateOf(shape.scale) }
    var rotation by remember { mutableFloatStateOf(shape.rotation) }

    // ViewModel
    val shapeViewModel: ShapeViewModel = hiltViewModel()

    // Get screen width and height
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }

    // Box containing the shape and gesture detection
    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    offset.x.toInt(),
                    offset.y.toInt(),
                )
            }
    ) {
        Column(
            modifier = Modifier
                .offset(y = (-10).dp)
                .background(Color(0x88FFFFFF))
                .padding(4.dp)
                .zIndex(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val shapeDetail =
                "X: ${offset.x.toInt()}, Y: ${offset.y.toInt()}" + "\n" +
                        "Rotation: ${rotation.toInt()}° " + "\n" +
                        "Scale: ${String.format("%.2f", scale)}"

            Text(
                text = shapeDetail,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }

        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation
                )
                .size(shape.size.dp * scale)
                .background(shape.color, shape.shapeType)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, rotationDelta ->
                        scale = (scale * zoom).coerceIn(0.5f, 2.0f)

                        rotation = (rotation + rotationDelta).let {
                            if (it < 0) it + 360 else it % 360
                        }

                        val angleRad = Math.toRadians(rotation.toDouble())
                        val adjustedPanX = (pan.x * cos(angleRad) - pan.y * sin(angleRad)).toFloat()
                        val adjustedPanY = (pan.y * cos(angleRad) + pan.x * sin(angleRad)).toFloat()

                        val shapeWidthPx = shape.size.dp.toPx() * scale
                        val shapeHeightPx = shape.size.dp.toPx() * scale
                        val newX = (offset.x + adjustedPanX).coerceIn(0f, screenWidthPx - shapeWidthPx)
                        val newY = (offset.y + adjustedPanY).coerceIn(0f, screenHeightPx - shapeHeightPx)
                        offset = Offset(newX, newY)

                        // Update ViewModel only when the state has changed
                        shapeViewModel.updateShapeValue(
                            shape.copy(
                                position = offset,
                                rotation = rotation,
                                scale = scale
                            ),
                            shape
                        )
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { remove() }
                    )
                }
        )
    }
}

