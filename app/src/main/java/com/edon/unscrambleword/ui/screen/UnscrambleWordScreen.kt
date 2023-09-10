package com.edon.unscrambleword.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edon.unscrambleword.viewmodel.UnscrambleWordViewModel

@Composable
fun IndexComp(currentWordIndex: Int, count: Int, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.extraSmall) {
        Text(
            modifier = Modifier.padding(3.dp),
            text = "${currentWordIndex+1}/$count",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.inverseOnSurface
        )
    }
}

@Preview
@Composable
fun index() {
    IndexComp(currentWordIndex = 2, count = 20)
}

@Composable
fun ScoreComp(score: Int, modifier: Modifier = Modifier){
    Surface(color = MaterialTheme.colorScheme.tertiary, shape = MaterialTheme.shapes.extraSmall) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = "Score: $score",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun Scoreprev() {
    ScoreComp(score = 5)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnscrambleWordScreen(viewModel: UnscrambleWordViewModel, onSkipClicked: () -> Unit, modifier: Modifier = Modifier) {
    //observe data from viewModel
    val currentWordIndex by viewModel.currentWordIndex.observeAsState()
    val score by viewModel.score.observeAsState()
    val scrambledWord by viewModel.scrambledWord.observeAsState()
    val listCount = viewModel.LISTCOUNT
    var userGuess by remember{ mutableStateOf("") }

    Column(
        modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(top = 72.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Row {
                    ScoreComp(score = score!!)
                    Spacer(modifier = Modifier.weight(1f))
                    IndexComp(
                        currentWordIndex!!,
                        listCount
                    )
                }
                Row(Modifier.align(Alignment.CenterHorizontally)) {
                    Text(
                        text = scrambledWord!!,
                        fontSize = 35.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Text(text = "Unscramble the word using all the letters")
                OutlinedTextField(
                    value = userGuess,
                    onValueChange = {userGuess = it},
                    Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )
            }
        }
        Button(onClick = {
            if(userGuess.isNotEmpty()){
                viewModel.submitGuess(userGuess.trim())
            }
            userGuess = ""
            },
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)) {
            Text(text = "Submit")
        }
        OutlinedButton(onClick = {
            onSkipClicked()
            userGuess = ""
            },
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)) {
            Text(text = "Skip")
        }

    }
}

@Preview
@Composable
fun Materiad() {
    UnscrambleWordScreen(viewModel = UnscrambleWordViewModel(), onSkipClicked = {})
}