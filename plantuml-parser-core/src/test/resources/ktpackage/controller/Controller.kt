package controller

import repository.MemoryRepository
import repository.Repository
import service.Service as AppService

class Controller(val service: AppService, fallback: Repository) {
    val history: List<AppService> = listOf()
    var current: MemoryRepository? = null

    fun handle(id: String): String {
        return service.run(id)
    }
}
