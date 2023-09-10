package com.edon.unscrambleword.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edon.unscrambleword.data.DataSource

/*
ViewModel to manage the game logic, including the list of words,
the current word, the user's score, and other game-related data.
This ViewModel will expose LiveData objects to update the UI.
 */
class UnscrambleWordViewModel: ViewModel() {
    private val _wordList = DataSource.dictionary
    private val _LISTCOUNT = _wordList.size
    val LISTCOUNT = _LISTCOUNT

    private val _currentWordIndex = MutableLiveData<Int>(0)
    val currentWordIndex: LiveData<Int> = _currentWordIndex

    private val _score = MutableLiveData<Int>(0)
    var score: LiveData<Int> = _score

    private val _scrambledWord = MutableLiveData<String>("")
    val scrambledWord: LiveData<String> = _scrambledWord

    private val _showFinalScore = MutableLiveData<Boolean>()
    val showFinalScore: LiveData<Boolean> = _showFinalScore

    //to scramble words
    private fun scrambler(word: String): String{
        var tempWord = word.toCharArray() // array of chars
        tempWord.shuffle()
        while(String(tempWord) == word){
            tempWord.shuffle() // shuffle again if word is same as shuffled
        }
        return String(tempWord)
    }

    private fun nextScrambledWord(){
        _currentWordIndex.value = _currentWordIndex.value!! + 1
        _scrambledWord.value = scrambler(_wordList[_currentWordIndex.value!!])
    }

    //to reset and start game
    fun startGame(){
        _currentWordIndex.value = 0
        _score.value = 0
        _scrambledWord.value = scrambler(_wordList[_currentWordIndex.value!!])
        _showFinalScore.value = false
    }

    //
    private fun showFinalScore(){
        _showFinalScore.value = true
    }

    fun onSummaryScreenNavigated(){
        _showFinalScore.value = false
    }

    //handle user inputs
    fun submitGuess(guess: String){
        if(_currentWordIndex.value!! >= LISTCOUNT - 1){
            showFinalScore()
        } else{
            // Check if the guess is correct, update score, and move to the next word.
            //_wordList[current index] will contain the correct word
            if(guess == _wordList[_currentWordIndex.value!!]){
                _score.value = _score.value!! + 1
                nextScrambledWord()
            } else{
                nextScrambledWord()
            }
        }
    }

    //skip word
    fun skipWord(){
        if(_currentWordIndex.value!! >= LISTCOUNT - 1){
            showFinalScore()
        } else {
            nextScrambledWord()
        }
    }

}