package com.example.flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.flashcards.R.id.imageView2
import com.example.flashcards.R.id.imageView3

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        findViewById<View>(imageView2).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(imageView3).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            val data = Intent() // create a new Intent, this is where we will put our data

            data.putExtra(
                "string1",
                "some string"
            ) // puts one string into the Intent, with the key as 'string1'

            data.putExtra(
                "string2",
                "another string"
            ) // puts another string into the Intent, with the key as 'string2

            setResult(RESULT_OK, data) // set result code and bundle data for response

            finish()
        }
    }
}