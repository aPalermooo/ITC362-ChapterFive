package com.missouristate.chapter_five

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.missouristate.chapter_five.databinding.ActivityMainBinding

private const val TAG = "MainActivity"



class MainActivity : AppCompatActivity() {

    private val quizViewModel : QuizViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    //chapter 7
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        //Handle the result
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN,false) ?: false
        }
    }

//    private val questionBank = listOf(
//        Question(R.string.question_australia,true),
//        Question(R.string.question_oceans,true),
//        Question(R.string.question_mideast,false),
//        Question(R.string.question_africa,false),
//        Question(R.string.question_americas,true),
//        Question(R.string.question_asia,true),
//    )
//
//    private var currentIndex = 0




    //lateinit allows initializing a not-null property outside of a constructor



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //BINDING SET UP
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate(Bundle) called")
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")


        //https://developer.android.com/develop/ui/views/notifications/snackbar/action

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
//            currentIndex = (currentIndex + 1) % questionBank.size
            quizViewModel.moveToNext()
            updateQuestion()
        }

        //Chapter 7.1
        binding.cheatButton.setOnClickListener() {
            //start CheatActivity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        updateQuestion()

    }

    //refactored code
    private fun updateQuestion() {
//        val questionTextResId = questionBank[currentIndex].textResId
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer (userAnswer:Boolean) {
//        val correctAnswer = questionBank[currentIndex].answer
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct
            else -> R.string.incorrect
        }
        Log.d(TAG,"${quizViewModel.isCheater}")
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }



    /// LOGGING (Assignment 3.1)

    override fun onStart(){
        super.onStart()
        Log.d(TAG,"onStart() called")
    }

    override fun onPause(){
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG,"onStop() called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }


}