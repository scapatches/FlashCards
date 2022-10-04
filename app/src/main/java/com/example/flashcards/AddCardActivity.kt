package com.example.flashcards


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText


import android.widget.ImageView

import androidx.activity.result.contract.ActivityResultContracts


class AddCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        val questionEditText = findViewById<EditText>(R.id.FlashCard_questionEditText)
        val answerEditText = findViewById<EditText>(R.id.FlashCard_answerEditText)
        val saveButton = findViewById<ImageView>(R.id.save_button)
        val cancelb = findViewById<ImageView>(R.id.cancel_button)

        saveButton.setOnClickListener{
            val questionSting = questionEditText.text.toString()
            val answerString = answerEditText.text.toString()

            val data = Intent()
            data.putExtra("QUESTION_KEY", questionSting )
            data.putExtra("ANSWER_KEY", answerString )
            setResult(RESULT_OK, data)

            finish()
        }
        cancelb.setOnClickListener{
    finish()
    }
    }
}