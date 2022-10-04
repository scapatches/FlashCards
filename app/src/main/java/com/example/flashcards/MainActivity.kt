package com.example.flashcards
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flashcardQuestion = findViewById<TextView>(R.id.question)
        val flashcardAnswer = findViewById<TextView>(R.id.answer)



        flashcardQuestion.setOnClickListener {
            flashcardQuestion.visibility = View.INVISIBLE
            flashcardAnswer.visibility = View.VISIBLE
        }
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            if (data != null) {
                val questionString = data.getStringExtra("QUESTION_KEY")
                val answerString = data.getStringExtra("ANSWER_KEY")
                flashcardQuestion.text = questionString
                flashcardAnswer.text = answerString
            }
        }
        findViewById<ImageView>(R.id.add_buttons).setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }

    }
}