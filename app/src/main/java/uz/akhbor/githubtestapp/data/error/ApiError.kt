package uz.akhbor.githubtestapp.data.error

class ApiError(val errorBody: String) : Exception(errorBody) {
}