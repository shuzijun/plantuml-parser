package repository

interface Repository {
    fun find(id: String): String
}
