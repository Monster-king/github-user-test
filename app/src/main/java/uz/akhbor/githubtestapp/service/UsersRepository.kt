package uz.akhbor.githubtestapp.service

import uz.akhbor.githubtestapp.base.response.ResponseResult
import uz.akhbor.githubtestapp.base.response.loadWithIo
import uz.akhbor.githubtestapp.data.mapper.transformList
import uz.akhbor.githubtestapp.domain.User
import uz.akhbor.githubtestapp.domain.UserDetail
import uz.akhbor.githubtestapp.service.api.UsersApi


class UsersRepository(
    private val api: UsersApi
) {

    suspend fun getUsers(perPage: Int, since: Int): ResponseResult<List<User>> {
        return loadWithIo(
            caller = {
                api.getUsers(perPage = perPage, since = since)
            },
            transformer = {
                it?.transformList()
            }
        )
    }

    suspend fun getUserDetail(login: String): ResponseResult<UserDetail> {
        return loadWithIo(
            caller = {
                api.getUser(login)
            },
            transformer = {
                it?.transform()
            }
        )
    }
}