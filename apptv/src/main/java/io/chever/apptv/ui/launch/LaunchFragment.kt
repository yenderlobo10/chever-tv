package io.chever.apptv.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import io.chever.apptv.R
import io.chever.apptv.databinding.FragmentLaunchBinding
import io.chever.apptv.model.ResourceState
import io.chever.apptv.ui.main.MainFragmentView
import io.chever.apptv.ui.main.MainViewModel
import io.chever.data.BuildConfig
import io.chever.shared.extension.showLongToast
import io.chever.shared.observability.AppLogger
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LaunchFragment : Fragment() {

    private lateinit var binding: FragmentLaunchBinding
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLaunchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {

        try {

            LaunchLogoAnimate(
                binding = binding,
                onFinished = {
                    it.runLoadingAnimate()
                    loadHomeCollections()
                }
            )

        } catch (ex: Exception) {
            AppLogger.error(ex)
        }
    }

    @Suppress("KotlinConstantConditions")
    private fun loadHomeCollections() {

        lifecycleScope.launch {

            if (BuildConfig.FLAVOR == "mock") delay(3.seconds)

            mainViewModel.loadHomeCollections().collect {
                if (it is ResourceState.Success)
                    whenMainStateSuccess()
                else if (it is ResourceState.Error)
                    requireContext().showLongToast(R.string.app_unknown_error_two)
                // TODO: show error dialog
            }
        }
    }

    private fun whenMainStateSuccess() {

        with((MainFragmentView.Home)) {
            parentFragmentManager.beginTransaction()
                .replace(android.R.id.content, this.fragment, this.tag)
                .commitAllowingStateLoss()
        }
    }

    companion object {

        const val TAG = "chever-launch-fragment"
    }
}