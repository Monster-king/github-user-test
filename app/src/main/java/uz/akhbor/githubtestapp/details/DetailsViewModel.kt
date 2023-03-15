package uz.akhbor.githubtestapp.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.akhbor.githubtestapp.base.response.error
import uz.akhbor.githubtestapp.base.response.success
import uz.akhbor.githubtestapp.service.UsersRepository
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private var detailsJob: Job? = null
    private val _detailsState = MutableStateFlow(DetailsState())
    val detailsState: Flow<DetailsState> = _detailsState.asStateFlow()

    fun loadDetails(login: String) {
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch {
            _detailsState.update {
                it.copy(loadState = LoadState.LOADING)
            }
            usersRepository.getUserDetail(login)
                .success { userDetail ->
                    _detailsState.update {
                        it.copy(
                            loadState = LoadState.SUCCESS,
                            userDetail = userDetail
                        )
                    }
                }
                .error {
                    _detailsState.update {
                        it.copy(loadState = LoadState.ERROR)
                    }
                }
        }
    }
}