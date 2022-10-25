package com.example.flashcards
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
            flashcardAnswer.visibility = View.VISIBLE
            flashcardQuestion.visibility = View.INVISIBLE
            val answerSideView = findViewById<TextView>(R.id.answer)
            val questionSideView = findViewById<TextView>(R.id.question)
// get the center for the clipping circle

// get the center for the clipping circle
            val cx = answerSideView.width / 2
            val cy = answerSideView.height / 2

// get the final radius for the clipping circle

// get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

// create the animator for this view (the start radius is zero)

// create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius)

// hide the question and show the answer to prepare for playing the animation!

// hide the question and show the answer to prepare for playing the animation!
            questionSideView.visibility = View.INVISIBLE
            answerSideView.visibility = View.VISIBLE

            anim.duration = 3000
            anim.start()
        }

        if (allFlashcards.size > 0) {
            findViewById<TextView>(R.id.question).text = allFlashcards[0].question
            findViewById<TextView>(R.id.answer).text = allFlashcards[0].answer
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

        nextCard.setOnClickListener {
            if (allFlashcards.size == 0) {
                // return here, so that the rest of the code in this onClickListener doesn't execute
                return@setOnClickListener

            }

            // advance our pointer index so we can show the next card


            // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list


            fun getRandomNumber(minNumber: Int, maxNumber: Int): Int {
                return (minNumber..maxNumber).random() // generated random from 0 to 10 included
            }
            deleteCard.setOnClickListener {
                val flashcardQuestionToDelete = flashcardQuestion.text.toString()
                flashcardDatabase.deleteCard(flashcardQuestionToDelete)
            }
            val leftOutAnim = AnimationUtils.loadAnimation(it.context, R.anim.left_out)
            val rightInAnim = AnimationUtils.loadAnimation(it.context, R.anim.animate)

            leftOutAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    flashcardQuestion.visibility = View.INVISIBLE
                    flashcardAnswer.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animation?) {
                    flashcardQuestion.startAnimation(rightInAnim)
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

                    flashcardQuestion.visibility = View.INVISIBLE
                    flashcardAnswer.visibility = View.VISIBLE

                }

                override fun onAnimationRepeat(animation: Animation?) {
                    // we don't need to worry about this method
                }
            })
            flashcardQuestion.startAnimation(leftOutAnim)


        }
        findViewById<ImageView>(R.id.add_buttons).setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
            overridePendingTransition(R.anim.animate, R.anim.left_out)

        }

    }

}