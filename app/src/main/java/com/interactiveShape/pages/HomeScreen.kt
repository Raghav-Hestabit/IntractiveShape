package com.interactiveShape.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.interactiveShape.viewModel.ShapeViewModel

@Composable
fun HomeScreen(shapeViewModel: ShapeViewModel) {
    val shapes by shapeViewModel.shapes.collectAsState()
    val context =  LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }

    LaunchedEffect(Unit) {
        if (shapes.isEmpty()){
            repeat(2){
                shapeViewModel.addShape(screenWidth,screenHeight)
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            shapes.forEach { shape ->
                if (shape != null) {
                    ShapeItem(
                        shape = shape,
                        remove = { shapeViewModel.removeItemFromList(shape.id) },
                    )
                }
            }
        }
        Button(
            onClick = {
                if (shapeViewModel.getNonNullShapeCount() < 10){
                    shapeViewModel.addShape(screenWidth, screenHeight)
                }else{
                    Toast.makeText(context, "Can not add more than 10 shapes", Toast.LENGTH_SHORT).show()
                }

            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Create Shape")
        }
    }
}

