package com.interactiveShape.viewModel

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interactiveShape.data.DataItem
import com.interactiveShape.data.GestureResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@HiltViewModel
class ShapeViewModel @Inject constructor() : ViewModel() {

    // Internal state for shapes
    private val _shapes =
        MutableStateFlow<List<DataItem?>>(emptyList()) // List with nullable ShapeData
    val shapes: StateFlow<List<DataItem?>> = _shapes


    private var shapeCounter = 3

    fun addShape(screenWidth: Float, screenHeight: Float) {
        viewModelScope.launch {
            var uniqueColor: Color
            do {
                uniqueColor = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            } while (_shapes.value.any { it!!.color == uniqueColor })
            val shapeTypes = listOf(CircleShape, RectangleShape)

            val randomShapeType = shapeTypes.random()


            val newShape = DataItem(
                id = shapeCounter++,
                color = uniqueColor,
                shapeType = randomShapeType,
                position = Offset(
                    x = Random.nextFloat() * screenWidth,
                    y = Random.nextFloat() * screenHeight
                )
            )

            // Update the state with the new shape
            _shapes.value += newShape
        }
    }




    fun getNonNullShapeCount(): Int {
        val nonNullShapes = _shapes.value.filterNotNull()
        if (nonNullShapes.isEmpty()) {
            _shapes.value = emptyList()
        }
        return nonNullShapes.size
    }


    fun removeItemFromList(shapeId: Int) {
        _shapes.value = _shapes.value.map { shape ->
            if (shape?.id == shapeId) null else shape
        }
    }


    fun updateShapeValue(updatedShapeData: DataItem, shapeData: DataItem) {
        val index = _shapes.value.indexOf(shapeData)

        if (index != -1) {
            _shapes.value = _shapes.value.toMutableList().apply {
                this[index] = this[index]?.copy(
                    position = updatedShapeData.position,
                    rotation = updatedShapeData.rotation,
                    scale = updatedShapeData.scale
                )
            }
        }
    }


    fun handleGesture(
        zoom: Float, rotationDelta: Float, pan: Offset, screenWidthPx: Float, screenHeightPx: Float,
        shapeSizePx: Float, currentScale: Float, currentRotation: Float, currentOffset: Offset
    ): GestureResult {
        val newScale = (currentScale * zoom).coerceIn(0.5f, 2.0f)

        val newRotation = (currentRotation + rotationDelta).let {
            if (it < 0) it + 360 else it % 360
        }

        val angleRad = Math.toRadians(newRotation.toDouble())
        val adjustedPanX = (pan.x * cos(angleRad) - pan.y * sin(angleRad)).toFloat()
        val adjustedPanY = (pan.y * cos(angleRad) + pan.x * sin(angleRad)).toFloat()

        val newX = (currentOffset.x + adjustedPanX).coerceIn(0f, screenWidthPx - shapeSizePx * newScale)
        val newY = (currentOffset.y + adjustedPanY).coerceIn(0f, screenHeightPx - shapeSizePx * newScale)

        return GestureResult(newScale, newRotation, Offset(newX, newY))
    }

}
