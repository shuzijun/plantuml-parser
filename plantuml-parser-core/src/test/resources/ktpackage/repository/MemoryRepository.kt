package repository

class MemoryRepository : Repository {
    override fun find(id: String): String {
        return id
    }
}
