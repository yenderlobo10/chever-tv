package io.chever.tv.ui.player.subtitles

import androidx.lifecycle.ViewModel
import io.chever.tv.api.opensubtitles.domain.models.OSSubtitleFile
import io.chever.tv.api.opensubtitles.domain.models.OSSubtitlesResult
import io.chever.tv.common.extension.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class SubtitlesViewModel : ViewModel() {

    private val repository: SubtitlesRepository by lazy { SubtitlesRepository() }
    private val languages by lazy { listOf("es", "en") }

    private val subtitlesList =
        MutableStateFlow<AppResult<OSSubtitlesResult>>(AppResult.Empty)


    fun findSubtitles(
        year: Int,
        query: String
    ) = flow {

        try {

            emit(AppResult.Loading)

            if (subtitlesList.value is AppResult.Success)
                emit(subtitlesList.value)
            else
                emit(
                    repository.subtitles(
                        year = year,
                        languages = languages,
                        query = query
                    )
                )

        } catch (ex: Exception) {

            Timber.e(ex)
            emit(AppResult.Error(ex.message))
        }
    }

    fun downloadSubtitle(file: OSSubtitleFile) = flow {

        try {

            emit(AppResult.Loading)

            emit(repository.download(file))

        } catch (ex: Exception) {

            Timber.e(ex)
            emit(AppResult.Error(ex.message))
        }
    }

}