package uz.akhbor.githubtestapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uz.akhbor.githubtestapp.base.EMPTY
import uz.akhbor.githubtestapp.data.mapper.Transformable
import uz.akhbor.githubtestapp.domain.User

@Serializable
class UserObj(
    @SerialName("login") val login: String,
    @SerialName("id") val id: Int? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
) : Transformable<User> {

    override fun transform(): User {
        return User(
            login = login,
            id = id ?: 0,
            avatarUrl = avatarUrl ?: String.EMPTY
        )
    }
}