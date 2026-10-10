package service

import repository.Repository

class Service(private val repository: Repository) {
    private val name: String = "service"

    fun run(id: String): String {
        return repository.find(id)
    }
}
