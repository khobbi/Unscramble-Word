package com.edon.unscrambleword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.edon.unscrambleword.viewmodel.UnscrambleWordViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = UnscrambleWordViewModel()
            viewModel.startGame()
            UnscrambleWordApp(viewModel)
        }
    }
}