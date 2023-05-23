package com.greenmate.greenmate.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.ActivityMainBinding
import com.greenmate.greenmate.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

//        binding.bottomNavigationView.setupWithNavController(navController)
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.chatRoomFragment, R.id.mainFragment, R.id.settingFragment -> {
//                    binding.bottomNavigationView.isVisible = true
//                }
//                else -> {
//                    binding.bottomNavigationView.isVisible = false
//                }
//            }
//        }

        navController.navigate(R.id.action_mainFragment_to_loadingFragment)

    }

}