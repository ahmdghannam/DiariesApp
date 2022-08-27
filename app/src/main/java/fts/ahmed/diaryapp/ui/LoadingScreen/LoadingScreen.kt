package fts.ahmed.diaryapp.ui.LoadingScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import fts.ahmed.diaryapp.R
import fts.ahmed.diaryapp.databinding.ActivityLoadingScreenBinding
import fts.ahmed.diaryapp.ui.MainActivity

class LoadingScreen : AppCompatActivity() {
    lateinit var binding: ActivityLoadingScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoadingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val animation=AnimationUtils.loadAnimation(applicationContext,R.anim.blink)
        binding.appTitle.startAnimation(animation)
        Handler().postDelayed({ initIntent() },1500)


    }

    private fun initIntent(){
        val intent=Intent(this@LoadingScreen,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}