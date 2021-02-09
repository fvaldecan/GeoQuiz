package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val EXTRA_ANSWER_SHOWN = "com.example.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.android.geoquiz.answer_is_true"
private const val KEY_CHEATED = "cheater"

class CheatActivity : AppCompatActivity() {
    private var answerIsTrue = false
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private var isCheater = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        isCheater = savedInstanceState?.getBoolean(KEY_CHEATED, false) ?: false
        setAnswerShownResult(isCheater)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener {
            isCheater = true
            showAnswer()
            setAnswerShownResult(isCheater)
        }
        showAnswer()
    }
    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_CHEATED, isCheater)
    }
    private fun showAnswer() {
        if (isCheater) {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
        }
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        if(isAnswerShown) {
            val data = Intent().apply {
                putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
            }
            setResult(Activity.RESULT_OK, data)
        }

    }
}