package com.example.geoquiz

// NOTE: Recommended (1. Readability 2. Prevents runtime crash)
import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean, var answered: Boolean, var cheatedOn: Boolean)

