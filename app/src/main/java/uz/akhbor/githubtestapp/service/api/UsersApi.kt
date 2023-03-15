package uz.akhbor.githubtestapp.service.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.akhbor.githubtestapp.data.UserDetailObj
import uz.akhbor.githubtestapp.data.UserObj

interface UsersApi {


    /**
     * Returns list of users registered in github
     *
     * @param perPage The number of results per page (max 100).
     * Default: 30
     * @param since A user ID. Only return users with an ID greater than this ID.
     */
    @GET("users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int = 30,
        @Query("since") since: Int
    ): Response<List<UserObj>>

    @GET("users/{login}")
    suspend fun getUser(@Path("login") login: String): Response<UserDetailObj>
}