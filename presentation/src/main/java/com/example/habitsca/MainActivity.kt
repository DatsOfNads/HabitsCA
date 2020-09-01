package com.example.habitsca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.habitsca.module.DaggerApplicationComponent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerApplicationComponent.create().inject(this)

        button.setOnClickListener {
           viewModel.click()
        }

        viewModel.subscribeAllHabits.observe(this, {
            textView.text = it.toString()
        })
    }
}