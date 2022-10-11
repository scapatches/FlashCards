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
import com.google.android.material.snackbar.Snackbar
import com.yourpackage.flashcards.Flashcard
import com.yourpackage.flashcards.FlashcardDatabase


class MainActivity : AppCompatActivity() {
    lateinit var flashcardDatabase: FlashcardDatabase
    var allFlashcards = mutableListOf<Flashcard>()
    var currentCardDisplayedIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        val flashcardQuestion = findViewById<TextView>(R.id.question)
        val flashcardAnswer = findViewById<TextView>(R.id.answer)
        val nextCard = findViewById<View>(R.id.next)
        val deleteCard = findViewById<View>(R.id.deleteBtn)


        flashcardQuestion.setOnClickListener {
            flashcardQuestion.visibility = View.INVISIBLE
            flashcardAnswer.visibility = View.VISIBLE
        }
        if (allFlashcards.size > 0) {
            flashcardQuestion.text = allFlashcards[0].question
            flashcardAnswer.text = allFlashcards[0].answer
        }
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            if (data != null) {
                val questionString = data.getStringExtra("QUESTION_KEY")
                val answerString = data.getStringExtra("ANSWER_KEY")
                flashcardQuestion.text = questionString
                flashcardAnswer.text = answerString

                if (questionString != null && answerString != null) {
                    flashcardDatabase.insertCard(Flashcard(questionString, answerString))
                    // Update set of flashcards to include new card
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                } else {
                    Log.e("TAG", "Missing question or answer to input into database. Question is $questionString and answer is $answerString")
                }
            }
        }
        findViewById<ImageView>(R.id.add_buttons).setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }
        nextCard.setOnClickListener {
            if (allFlashcards.size == 0) {
                // return here, so that the rest of the code in this onClickListener doesn't execute
                return@setOnClickListener
            }

            // advance our pointer index so we can show the next card
            currentCardDisplayedIndex++

            // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
            if(currentCardDisplayedIndex >= allFlashcards.size) {
                Snackbar.make(
                    flashcardQuestion, // This should be the TextView for displaying your flashcard question
                    "You've reached the end of the cards, going back to start.",
                    Snackbar.LENGTH_SHORT)
                    .show()
                currentCardDisplayedIndex = 0
            }

            // set the question and answer TextViews with data from the database
            allFlashcards = flashcardDatabase.getAllCards().toMutableList()
            val (question, answer) = allFlashcards[currentCardDisplayedIndex]

            flashcardAnswer.text = answer
            flashcardQuestion.text = question

            fun getRandomNumber(minNumber: Int, maxNumber: Int): Int {
                return (minNumber..maxNumber).random() // generated random from 0 to 10 included
            }
            deleteCard.setOnClickListener {
                val flashcardQuestionToDelete = flashcardQuestion.text.toString()
                flashcardDatabase.deleteCard(flashcardQuestionToDelete)
            }
        }

    }
}