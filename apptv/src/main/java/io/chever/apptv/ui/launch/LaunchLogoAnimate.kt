package io.chever.apptv.ui.launch

import android.view.Gravity
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.transition.ChangeTransform
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import io.chever.apptv.R
import io.chever.apptv.databinding.FragmentLaunchBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("MemberVisibilityCanBePrivate")
class LaunchLogoAnimate(
    private val binding: FragmentLaunchBinding,
    private val onFinished: (LaunchLogoAnimate) -> Unit
) {

    private val listOfLetters = listOf(
        binding.tvLogoLetter1,
        binding.tvLogoLetter2,
        binding.tvLogoLetter3,
        binding.tvLogoLetter4,
        binding.tvLogoLetter5,
        binding.tvLogoLetter6,
    )
    private val resLogoLetters =
        binding.root.context.resources.getStringArray(R.array.app_name_letters)

    private val logoTransitionScene = TransitionSet().apply {
        duration = 600
        addTransition(ChangeTransform())
        interpolator = FastOutSlowInInterpolator()
        addTarget(binding.ivLogo)
    }


    init {
        runAnimate()
    }

    fun runAnimate() {

        binding.root.findViewTreeLifecycleOwner()
            ?.lifecycleScope?.launch {
                runLogoTransitionScene()
                runLettersTransitionScene()
            }
    }

    @Suppress("DeferredResultUnused")
    fun runLoadingAnimate() {

        binding.root.findViewTreeLifecycleOwner()
            ?.lifecycleScope?.launch {
                async { runLogoLoadingTransitionScene() }
                runLettersLoadingTransitionScene()
            }
    }


    private suspend fun runLogoTransitionScene() {

        delay(10)

        TransitionManager.beginDelayedTransition(
            binding.root,
            logoTransitionScene
        )

        with(binding.ivLogo) {
            scaleY = 1f
            scaleX = 1f
        }
    }

    private suspend fun runLogoLoadingTransitionScene() {

        var toggle = true

        while (true) {

            TransitionManager.beginDelayedTransition(
                binding.root,
                logoTransitionScene
            )

            with(binding.ivLogo) {
                val scale = if (toggle) 0.8f else 1f
                scaleY = scale
                scaleX = scale
            }

            toggle = toggle.not()
            delay(600)
        }
    }


    private suspend fun runLettersTransitionScene() {

        val transitionScene = TransitionSet().apply {
            duration = 500
            addTransition(Slide(Gravity.BOTTOM))
            interpolator = FastOutSlowInInterpolator()
        }

        delay(390)

        listOfLetters.forEachIndexed { i, textView ->

            textView.text = resLogoLetters[i]
            TransitionManager.beginDelayedTransition(binding.root, transitionScene)
            textView.visibility = View.VISIBLE

            delay(150)
        }

        delay(500)

        onFinished.invoke(this)
    }

    private suspend fun runLettersLoadingTransitionScene() {

        val transitionScene = TransitionSet().apply {
            duration = 200
            addTransition(ChangeTransform())
            interpolator = FastOutSlowInInterpolator()
        }
        var toggle = true

        while (true) {

            listOfLetters.forEach {

                TransitionManager.beginDelayedTransition(
                    binding.root,
                    transitionScene
                )

                with(it) {
                    val scale = if (toggle) 1.2f else 1f
                    scaleY = scale
                    scaleX = scale
                }

                delay(100)
            }

            toggle = toggle.not()
        }
    }

}