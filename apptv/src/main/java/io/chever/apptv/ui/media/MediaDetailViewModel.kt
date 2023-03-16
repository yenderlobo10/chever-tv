package io.chever.apptv.ui.media

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chever.apptv.model.ResourceState
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.detail.PersonCast
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.resource.AppResult
import io.chever.domain.usecase.media.GetMediaItemCreditsUseCase
import io.chever.domain.usecase.media.GetMediaItemRecommendationsUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    private val getMediaItemCreditsUseCase: GetMediaItemCreditsUseCase,
    private val getMediaItemRecommendationsUseCase: GetMediaItemRecommendationsUseCase
) : ViewModel() {

    private val _cast = MutableStateFlow<ResourceState<List<PersonCast>>>(ResourceState.Empty)
    val cast: StateFlow<ResourceState<List<PersonCast>>> = _cast

    private val _recommendations =
        MutableStateFlow<ResourceState<List<MediaItem>>>(ResourceState.Empty)
    val recommendations: StateFlow<ResourceState<List<MediaItem>>> = _recommendations


    suspend fun loadCast(
        id: Long,
        mediaType: MediaTypeEnum
    ) {

        if (_cast.value is ResourceState.Success) return

        _cast.value = ResourceState.Loading

        getMediaItemCreditsUseCase(
            params = GetMediaItemCreditsUseCase.Params(
                id = id,
                mediaType = mediaType
            )
        ) {
            _cast.value = when (it) {
                is AppResult.Success -> ResourceState.Success(it.value)
                is AppResult.Failure -> ResourceState.Error(issue = it.value)
            }
        }
    }

    suspend fun loadRecommendations(
        id: Long,
        mediaType: MediaTypeEnum
    ) {

        if (_recommendations.value is ResourceState.Success) return

        _recommendations.value = ResourceState.Loading

        getMediaItemRecommendationsUseCase(
            params = GetMediaItemRecommendationsUseCase.Params(
                id = id,
                mediaType = mediaType
            )
        ) {
            _recommendations.value = when (it) {
                is AppResult.Success -> ResourceState.Success(it.value)
                is AppResult.Failure -> ResourceState.Error(issue = it.value)
            }
        }
    }

}