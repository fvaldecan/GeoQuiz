package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView

    private var currentIndex = 0
    private var totalAnswered = 0
    private var answersCorrect = 0

    private lateinit var cheatButton: Button
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex


//        References content view
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton = findViewById(R.id.cheat_button)


//        Button onClick Listeners
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }
        prevButton.setOnClickListener {
            if (totalAnswered == quizViewModel.questionBankSize) {
                showGrade()
            }
            quizViewModel.moveToPrev()
            updateQuestion()
        }
        nextButton.setOnClickListener {
            if (totalAnswered == quizViewModel.questionBankSize) {
                showGrade()
            }
            quizViewModel.moveToNext()
            updateQuestion()
        }
        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        updateQuestion()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK){
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            val questionCheatState = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            quizViewModel.changeCheatState(questionCheatState)
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }


    override fun onSaveInstanceState(saveInstanceState: Bundle) {
        super.onSaveInstanceState(saveInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        saveInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        isAnswered()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        quizViewModel.changeAnsweredState(true)
      
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if(userAnswer == correctAnswer) answersCorrect += 1
      
        val messageResId = when{
            quizViewModel.currentCheatState -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        totalAnswered += 1
        Log.d(TAG, "checkAnswered() called $totalAnswered")
        isAnswered()
    }

    private fun isAnswered() {
        val answered = quizViewModel.currentAnsweredState
        trueButton.isEnabled = !answered
        falseButton.isEnabled = !answered
    }

    private fun showGrade() {

        val percent = (answersCorrect * 100) / quizViewModel.questionBankSize
        Log.d(TAG, "showGrade() called $percent")

        val toast = Toast.makeText(this, "$percent % correct", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }
}