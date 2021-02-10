package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel(){
    private val questionBank = listOf(
        Question(R.string.question_australia, false, false,false),
        Question(R.string.question_oceans, true, false,false),
        Question(R.string.question_mideast, true, false,false),
        Question(R.string.question_africa, true, false,false),
        Question(R.string.question_americas, true, false,false),
        Question(R.string.question_asia, true, false,false)
    )
    var currentIndex = 0
    var isCheater = false
//    get() is simple getter to variable
    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentIndex].textResId
    val currentCheatState: Boolean get() = questionBank[currentIndex].cheatedOn
    val currentAnsweredState: Boolean get() = questionBank[currentIndex].answered
    val questionBankSize: Int get() = questionBank.size
    fun changeCheatState(cheatState: Boolean){
        questionBank[currentIndex].cheatedOn = cheatState
    }
    fun changeAnsweredState(answeredState: Boolean){
        questionBank[currentIndex].answered = answeredState
    }
    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev(){
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
    }
}