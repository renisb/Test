package com.example.renata.apitest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.renata.apitest.MainActivity
import com.example.renata.apitest.R
import kotlinx.android.synthetic.main.activity_nav_menu.*

class NavMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_menu)
        //val page:Int=0
        btnClose.setOnClickListener {
            startActivity(Intent(this@NavMenu, MainActivity::class.java))
        }
        textEntrance.setOnClickListener {
            MainActivity.page = 0
            startActivity(Intent(this@NavMenu, MainActivity::class.java))
        }
        textExit.setOnClickListener {
            MainActivity.page = 1
            startActivity(Intent(this@NavMenu, MainActivity::class.java))
        }
    }
}