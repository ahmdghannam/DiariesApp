package fts.ahmed.diaryapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import fts.ahmed.diaryapp.R
import fts.ahmed.diaryapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.parent.setOnTouchListener { view, motionEvent ->
//
//             search about this
//        }
        bindingInit()
        navigationInit()


    }

    private fun bindingInit() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun navigationInit() {
        val navController = findNavController(this, R.id.host_fragment)
        setupWithNavController(binding.bottomNavigation, navController)
//        binding.bottomNavigation.selectedItemId = R.id.createDiaryFragment

    }
}