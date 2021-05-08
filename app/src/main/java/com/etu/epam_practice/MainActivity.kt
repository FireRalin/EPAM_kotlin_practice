package com.etu.epam_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etu.epam_practice.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.stopStarButton.setOnClickListener{
            val temp = null
        }

        binding.previosButton.setOnClickListener{
            val temp = null
        }

        binding.nextButton.setOnClickListener{
            val temp = null
        }
    }
}