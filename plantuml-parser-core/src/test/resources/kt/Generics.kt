package kt

interface Repository<T> {
    fun findAll(): Array<T>
}

class CustomRepo: Repository<String> {
    override fun findAll(): Array<String> {
        return arrayOf("Sorted1, Sorted2")
    }
}
fun main() {
    val customRepo = CustomRepo()
    println(customRepo.findAll().joinToString())
}