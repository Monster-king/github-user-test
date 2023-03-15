package uz.akhbor.githubtestapp.data.mapper

interface Transformable<T> {
    fun transform(): T
}

fun <T> List<Transformable<T>>.transformList(): List<T> {
    return map { it.transform() }
}