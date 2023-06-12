package com.jet.jetnet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jet.jetnet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)




        viewBinding.tvTest.setOnClickListener {


        }
    }
}