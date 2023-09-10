package com.edon.unscrambleword.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edon.unscrambleword.viewmodel.UnscrambleWordViewModel

@Composable
fun SummaryScreen(viewModel: UnscrambleWordViewModel, onResetClicked: () -> Unit, modifier: Modifier = Modifier) {
    val passed: Boolean = viewModel.score.value!! >= (viewModel.LISTCOUNT/2)

    val scoreRemark: String = if(passed){
        "Congratulations!!!"
    } else {
        "Eeeeeew!"
    }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.medium) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(Modifier.align(Alignment.CenterHorizontally)) {
                        Text(
                            text = scoreRemark,
                            fontSize = 35.sp,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Text(text = "Your final score: ${viewModel.score.value!!}/${viewModel.LISTCOUNT}",
                        fontSize = 20.sp)

                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = onResetClicked, Modifier.padding(top = 50.dp)) {
                            Text(text = if (passed) "Restart" else "Try Again")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SumaPre() {
    SummaryScreen(viewModel = UnscrambleWordViewModel(), onResetClicked = {})
}