package com.example.lesson10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.lesson10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        mo man hinh insertion
//        val btn_insertion = findViewById<Button>(R.id.btnInsertData)
        binding.btnInsertData.setOnClickListener {
            val intent  = Intent(this,InsertionActivity::class.java)
            startActivity(intent)

        }
        binding.btnFetchData.setOnClickListener {
            val intent  = Intent(this,FetchingActivity::class.java)
            startActivity(intent)

        }

    }
}