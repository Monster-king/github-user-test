package uz.akhbor.githubtestapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uz.akhbor.githubtestapp.base.EMPTY
import uz.akhbor.githubtestapp.base.ext.tryParseDateTime
import uz.akhbor.githubtestapp.data.mapper.Transformable
import uz.akhbor.githubtestapp.domain.UserDetail

@Serializable
class UserDetailObj(
    @SerialName("login") val login: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("organizations_url") val organizationsUrl: String? = null,
    @SerialName("followers") val followers: Int? = null,
    @SerialName("following") val following: Int? = null,
    @SerialName("created_at") val createdAt: String? = null,
) : Transformable<UserDetail> {

    override fun transform(): UserDetail {
        return UserDetail(
            login = login,
            avatarUrl = avatarUrl ?: String.EMPTY,
            email = email ?: String.EMPTY,
            organizationsUrl = organizationsUrl ?: String.EMPTY,
            followers = followers ?: 0,
            following = following ?: 0,
            createdAt = tryParseDateTime(createdAt)
        )
    }
}