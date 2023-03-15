package uz.akhbor.githubtestapp.details

import uz.akhbor.githubtestapp.domain.UserDetail

internal data class DetailsState(
    val userDetail: UserDetail? = null,
    val loadState: LoadState = LoadState.LOADING
) {
    val isLoading = loadState.isLoading
    val isError = loadState == LoadState.ERROR
}

enum class LoadState(val isLoading: Boolean) {
    LOADING(true), ERROR(false), SUCCESS(false)
}