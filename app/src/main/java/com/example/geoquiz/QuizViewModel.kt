package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel(){
    private val questionBank = listOf(
        Question(R.string.q1, true, false,false),
        Question(R.string.q2, false, false,false),
        Question(R.string.q3, false, false,false),
        Question(R.string.q4, true, false,false),
        Question(R.string.q5, true, false,false),
        Question(R.string.q6, false, false,false),
        Question(R.string.q7, false, false,false),
        Question(R.string.q8, false, false,false),
        Question(R.string.q9, true, false,false),
        Question(R.string.q10, false, false,false)
    )
    var currentIndex = 0
//    get() is simple getter to variable
    var totalAnswered = 0
    var totalCorrect = 0
    val completed: Boolean get() = totalAnswered == questionBankSize
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