package uz.akhbor.githubtestapp.base.response

/**
 * Base Response Processing Class
 */
sealed class ResponseResult<out R> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val exception: Exception) : ResponseResult<Nothing>()
}