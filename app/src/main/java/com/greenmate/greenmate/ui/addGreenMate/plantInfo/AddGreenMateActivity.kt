package com.greenmate.greenmate.ui.addGreenMate.plantInfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.ActivityAddGreenMateBinding
import com.greenmate.greenmate.databinding.ActivityMainBinding
import com.greenmate.greenmate.ui.detail.DetailFragmentDirections

class AddGreenMateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGreenMateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGreenMateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.run {
            setupWithNavController(navController, appBarConfiguration)
            setNavigationIcon(R.drawable.icon_back_arrow)
            setTitleTextColor(
                ContextCompat.getColor(
                    this@AddGreenMateActivity,
                    android.R.color.transparent
                )
            )
        }
    }
}