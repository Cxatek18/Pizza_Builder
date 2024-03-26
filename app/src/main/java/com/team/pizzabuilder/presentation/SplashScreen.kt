package com.team.pizzabuilder.presentation

import android.animation.Animator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieDrawable
import com.team.pizzabuilder.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startSplashScree()
    }

    private fun setSettingsAnimation() {
        with(binding) {
            lottieViewSplashScreen.repeatCount = 0
            lottieViewSplashScreen.repeatMode = LottieDrawable.RESTART
            lottieViewSplashScreen.playAnimation()
        }
    }

    private fun startSplashScree() {
        setSettingsAnimation()
        binding.lottieViewSplashScreen.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                val intent: Intent = MainActivity.newInstance(
                    context = this@SplashScreen
                )
                val enterAnimation = com.google.android.material
                    .R.anim.linear_indeterminate_line1_head_interpolator
                val exitAnimation = androidx.appcompat.R.anim.abc_fade_out
                val activityOptions = ActivityOptions.makeCustomAnimation(
                    applicationContext, enterAnimation, exitAnimation
                )
                startActivity(intent, activityOptions.toBundle())
                finish()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }
}