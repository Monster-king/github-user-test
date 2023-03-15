package uz.akhbor.githubtestapp.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.akhbor.githubtestapp.base.ext.change
import uz.akhbor.githubtestapp.domain.User
import uz.akhbor.githubtestapp.service.UsersRepository
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
) : ViewModel() {

    private val usersList: Flow<PagingData<User>> = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 30)
    ) {
        UsersPagingSource(usersRepository)
    }
        .flow
        .cachedIn(viewModelScope)
    val usersState = MutableStateFlow(UsersState())

    init {
        viewModelScope.launch {
            usersList.collectLatest { pagingData ->
                usersState.change {
                    it.copy(
                        pagingData = pagingData,
                        isLoading = false
                    )
                }
            }
        }
    }
}