package uz.akhbor.githubtestapp.users

import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.akhbor.githubtestapp.base.response.empty
import uz.akhbor.githubtestapp.base.response.error
import uz.akhbor.githubtestapp.base.response.success
import uz.akhbor.githubtestapp.domain.User
import uz.akhbor.githubtestapp.service.UsersRepository
import javax.inject.Inject

class UsersPagingSource @Inject constructor(
    private val usersRepository: UsersRepository
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val offset = when (params) {
            is LoadParams.Refresh -> 0
            is LoadParams.Append -> params.key
            is LoadParams.Prepend -> params.key
        }

        val responseResult = usersRepository.getUsers(params.loadSize, offset)

        responseResult.success { users ->
            return LoadResult.Page(
                data = users,
                prevKey = null,
                nextKey = users.lastOrNull()?.id
            )
        }.error {
            return LoadResult.Error(it)
        }.empty {
            return LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
        }
        return LoadResult.Invalid()
    }
}