package com.example.habitsca.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.habitsca.App
import com.example.habitsca.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_header.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

   // @Inject lateinit var viewModel: MainActivityViewModel

    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //(application as App).component.inject(this)

        appBarConfig = AppBarConfiguration(findNavController(R.id.fragment).graph, drawerLayout)

        navigationView.setupWithNavController(findNavController(R.id.fragment))
        navigationView.inflateHeaderView(R.layout.navigation_header)

        val navDriver = navigationView.getHeaderView(0)
        val imageView = navDriver.imageView

        Glide.with(applicationContext)
            .load("https://i.ibb.co/cbcVQzj/Adobe-Post-20200820-1933020-05235088592940795.png")
            .override(150, 150)
            //.placeholder(R.drawable.placeholder)
            //      .error(R.drawable.imagenotfound)
            .circleCrop()
            .into(imageView)

        setupActionBarWithNavController(findNavController(R.id.fragment), appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)

        return navController.navigateUp(appBarConfig) ||
                super.onSupportNavigateUp()
    }
}