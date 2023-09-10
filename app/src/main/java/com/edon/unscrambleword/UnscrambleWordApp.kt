package com.edon.unscrambleword

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edon.unscrambleword.ui.screen.SummaryScreen
import com.edon.unscrambleword.ui.screen.UnscrambleWordScreen
import com.edon.unscrambleword.ui.theme.UnscrambleWordTheme
import com.edon.unscrambleword.viewmodel.UnscrambleWordViewModel

enum class UnscrambleWordApp {
    QUIZ, SUMMARY
}

@Composable
fun UnscrambleWordApp(viewModel: UnscrambleWordViewModel, navHostController: NavHostController = rememberNavController()) {

    val showFinalScore by viewModel.showFinalScore.observeAsState()

    UnscrambleWordTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //navhost
            NavHost(navController = navHostController, startDestination = UnscrambleWordApp.QUIZ.name){
                //define routes
                composable(route = UnscrambleWordApp.QUIZ.name){
                    UnscrambleWordScreen(viewModel, onSkipClicked = { viewModel.skipWord() })
                }
                composable(route = UnscrambleWordApp.SUMMARY.name){
                    SummaryScreen(
                        viewModel = viewModel,
                        onResetClicked = {
                            //reset game and pop back stack of nav controller
                            viewModel.startGame()
                            navHostController.popBackStack(UnscrambleWordApp.QUIZ.name, false)
                        }
                    )
                }

                //navHostController.navigate(UnscrambleWordApp.SUMMARY.name)
            }
        }
    }
    //navigate
    if(showFinalScore!!){
        navHostController.navigate(UnscrambleWordApp.SUMMARY.name)
        viewModel.onSummaryScreenNavigated() //reset the navigation trigger
    }
}

@Preview
@Composable
fun MainPrev() {
    val viewModel = UnscrambleWordViewModel()
    viewModel.startGame()
    UnscrambleWordTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            UnscrambleWordApp(viewModel)
        }
    }
}