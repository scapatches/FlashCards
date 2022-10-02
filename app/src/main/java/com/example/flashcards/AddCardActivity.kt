package com.example.flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.flashcards.R.id.imageView2

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val s1 = intent.getStringExtra("stringKey1") // this string will be 'harry potter`
        val s2 = intent.getStringExtra("stringKey2") // this string will be 'voldemort'
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        findViewById<View>(imageView2).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val intent = Intent(this, AddCardActivity::class.java)
        intent.putExtra("stringKey1", "harry potter")
        intent.putExtra("stringKey2", "voldemort")
        resultLauncher.launch(intent)
    }
}