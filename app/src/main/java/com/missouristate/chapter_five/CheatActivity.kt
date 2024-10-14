package com.missouristate.chapter_five

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.missouristate.chapter_five.databinding.ActivityCheatBinding

private const val TAG = "CheatActivity"
private const val EXTRA_ANSWER_IS_TRUE = "com.missouristate.chapter_five.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.missouristate.chapter_sever.answer_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_cheat)
        //inflate binding
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieve answer value from intent message
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        binding.showAnswerButton.setOnClickListener() {
            val answerText = when {
                answerIsTrue -> R.string.correct
                else -> R.string.incorrect
            }
            binding.answerTextView.setText(answerText)

            setAnswerShownResult(true)

            Log.d(TAG,"applied")
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK,data)
    }

    companion object {
        //static class (one instance)
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            Log.d(TAG,"Cheater!")
            return Intent(packageContext,CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}

