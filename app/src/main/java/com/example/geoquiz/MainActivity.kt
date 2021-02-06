package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private val questionBank = listOf(
            Question(R.string.question_australia, true, false),
            Question(R.string.question_oceans, true, false),
            Question(R.string.question_mideast, false, false),
            Question(R.string.question_africa, false, false),
            Question(R.string.question_americas, true, false),
            Question(R.string.question_asia, true, false)
    )
    private var currentIndex = 0
    private var totalAnswered = 0
    private var answersCorrect = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)


//        References content view
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)


//        Button onClick Listeners
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            if (totalAnswered == questionBank.size) {
                showGrade()
            }
            updateQuestion()
        }
        updateQuestion()

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

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        isAnswered()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        questionBank[currentIndex].answered = true
        val messageResId = if (userAnswer == correctAnswer) {
            answersCorrect += 1
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        totalAnswered += 1
        Log.d(TAG, "checkAnswered() called $totalAnswered")
        isAnswered()
    }

    private fun isAnswered() {
        val answered = questionBank[currentIndex].answered
        trueButton.isEnabled = !answered
        falseButton.isEnabled = !answered
    }

    private fun showGrade() {

        val percent = (answersCorrect * 100) / questionBank.size
        Log.d(TAG, "showGrade() called $percent")

        val toast = Toast.makeText(this, "$percent % correct", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }
}