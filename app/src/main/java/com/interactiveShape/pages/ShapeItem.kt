package com.interactiveShape.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.interactiveShape.data.DataItem
import com.interactiveShape.data.GestureResult
import com.interactiveShape.viewModel.ShapeViewModel
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun ShapeItem(
    shape: DataItem,
    remove: () -> Unit
) {
    var offset by remember { mutableStateOf(shape.position) }
    var scale by remember { mutableFloatStateOf(shape.scale) }
    var rotation by remember { mutableFloatStateOf(shape.rotation) }

    val shapeViewModel: ShapeViewModel = hiltViewModel()
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }

    Box(
        modifier = Modifier
            .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
    ) {
        ShapeDetailOverlay(offset, rotation, scale)

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
                        shapeViewModel.handleGesture(
                            zoom, rotationDelta, pan, screenWidthPx, screenHeightPx,
                            shape.size.dp.toPx(), scale, rotation, offset
                        ).let {
                            scale = it.scale
                            rotation = it.rotation
                            offset = it.offset
                        }

                        shapeViewModel.updateShapeValue(
                            shape.copy(position = offset, rotation = rotation, scale = scale),
                            shape
                        )
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = { remove() })
                }
        )
    }
}




