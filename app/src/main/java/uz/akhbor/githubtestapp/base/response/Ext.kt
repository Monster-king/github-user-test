package uz.akhbor.githubtestapp.base.response

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import uz.akhbor.githubtestapp.base.EMPTY
import uz.akhbor.githubtestapp.data.error.ApiError
import uz.akhbor.githubtestapp.data.mapper.Transformable
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Response Result success
 */
inline infix fun <T> ResponseResult<T>.success(predicate: (data: T) -> Unit): ResponseResult<T> {
    if (this is ResponseResult.Success && this.data != null) {
        predicate.invoke(this.data)
    }
    return this
}

/**
 * Response Result success null data
 */
inline infix fun <T> ResponseResult<T>.empty(predicate: () -> Unit): ResponseResult<T> {
    if (this is ResponseResult.Success && this.data == null) {
        predicate.invoke()
    }
    return this
}

/**
 * Response Result error
 */
inline infix fun <T> ResponseResult<T>.error(predicate: (data: Exception) -> Unit): ResponseResult<T> {
    if (this is ResponseResult.Error) {
        if (this.exception !is UnknownHostException && this.exception !is SocketTimeoutException) {
            predicate.invoke(this.exception)
        }
    }
    return this
}

suspend fun <R, T> loadWithIo(
    caller: suspend () -> Response<T>,
    transformer: (T?) -> R?
): ResponseResult<R> {
    return withContext(Dispatchers.IO) {
        try {
            val response = caller()
            if (response.isSuccessful) {
                ResponseResult.Success(
                    transformer(response.body())
                        ?: throw IllegalStateException("Backend returned incorrect result")
                )
            } else {
                ResponseResult.Error(
                    ApiError(errorBody = response.errorBody()?.string() ?: String.EMPTY)
                )
            }
        } catch (e: Exception) {
            ResponseResult.Error(e)
        }
    }
}