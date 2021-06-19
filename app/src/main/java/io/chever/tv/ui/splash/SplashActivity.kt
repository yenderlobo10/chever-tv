package io.chever.tv.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.*
import com.orhanobut.logger.Logger
import io.chever.tv.R
import io.chever.tv.api.trakttv.TraktTV
import io.chever.tv.ui.home.view.HomeActivity
import kotlinx.coroutines.*

class SplashActivity : Activity() {

    private lateinit var layoutContainer: ConstraintLayout
    private lateinit var ivLogo: ImageView
    private lateinit var tvLogoLetter1: TextView
    private lateinit var tvLogoLetter2: TextView
    private lateinit var tvLogoLetter3: TextView
    private lateinit var tvLogoLetter4: TextView
    private lateinit var tvLogoLetter5: TextView
    private lateinit var tvLogoLetter6: TextView

    private lateinit var homeIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        try {

            setupUI()

        } catch (ex: Exception) {

            ex.printStackTrace()
        }
    }


    private fun setupUI() {

        layoutContainer = findViewById(R.id.layoutContainer)
        ivLogo = findViewById(R.id.ivLogo)
        tvLogoLetter1 = findViewById(R.id.tvLogoLetter1)
        tvLogoLetter2 = findViewById(R.id.tvLogoLetter2)
        tvLogoLetter3 = findViewById(R.id.tvLogoLetter3)
        tvLogoLetter4 = findViewById(R.id.tvLogoLetter4)
        tvLogoLetter5 = findViewById(R.id.tvLogoLetter5)
        tvLogoLetter6 = findViewById(R.id.tvLogoLetter6)

        homeIntent = Intent(this, HomeActivity::class.java)

        runLogoAnimate()
    }


    private fun runLogoAnimate() {

        GlobalScope.launch(Dispatchers.Main) {

            runLogoTransitionScene()
            runLettersTransitionScene()
        }
    }

    private suspend fun runLogoTransitionScene() {

        delay(10)

        val transitionScene = TransitionSet().apply {
            duration = 600
            addTransition(ChangeTransform())
            interpolator = FastOutSlowInInterpolator()
            addTarget(ivLogo)
        }

        TransitionManager.beginDelayedTransition(layoutContainer, transitionScene)

        ivLogo.scaleY = 1f
        ivLogo.scaleX = 1f
    }

    private suspend fun runLettersTransitionScene() {

        delay(390)

        listOf(
            tvLogoLetter1,
            tvLogoLetter2,
            tvLogoLetter3,
            tvLogoLetter4,
            tvLogoLetter5,
            tvLogoLetter6,
        ).forEach {

            val transitionScene = TransitionSet().apply {
                duration = 500
                addTransition(Slide(Gravity.BOTTOM))
                interpolator = FastOutSlowInInterpolator()
            }

            TransitionManager.beginDelayedTransition(layoutContainer, transitionScene)

            it.visibility = View.VISIBLE

            delay(150)
        }

        delay(500)

        goToHomeActivity()
    }


    private fun goToHomeActivity() {

        try {

            startActivity(homeIntent)
            finish()

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
        }
    }
}