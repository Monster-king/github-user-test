package uz.akhbor.githubtestapp.users

import androidx.paging.PagingData
import uz.akhbor.githubtestapp.domain.User

data class UsersState(
    val isLoading: Boolean = true,
    val pagingData: PagingData<User> = PagingData.empty(),
)