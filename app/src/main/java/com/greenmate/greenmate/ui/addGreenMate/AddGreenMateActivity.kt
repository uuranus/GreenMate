package com.greenmate.greenmate.ui.addGreenMate

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.ActivityAddGreenMateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGreenMateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGreenMateBinding
    private val addGreenMateViewModel: AddGreenMateViewModel by viewModels()
    private val navArgs: AddGreenMateActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGreenMateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController


        if (navArgs.addModule == 1) {
            navController.setGraph(R.navigation.add_module_navigation)
        } else {
            navController.setGraph(R.navigation.add_green_mate_navigation)
        }

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

        addGreenMateViewModel.setModuleAdded(navArgs.addModule == 1)
    }
}