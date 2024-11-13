package com.interactiveShape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.interactiveShape.pages.HomeScreen
import com.interactiveShape.ui.theme.IntractiveShapeTheme
import com.interactiveShape.viewModel.ShapeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: ShapeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntractiveShapeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

