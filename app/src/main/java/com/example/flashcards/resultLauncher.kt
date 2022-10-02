package com.example.flashcards

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flashcards.R.id.imageView2
class resultLauncher : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        // This code is executed in StartingActivity after we come back from EndingActivity

        // This extracts any data that was passed back from EndingActivity
        val data: Intent? = result.data
        // ToDo: Execute more code here

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_launcher)


    }
}