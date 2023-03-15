package uz.akhbor.githubtestapp.base.ext

import kotlinx.coroutines.flow.MutableStateFlow

suspend fun <T> MutableStateFlow<T>.change(body: (T) -> T) {
    emit(body(this.value))
}