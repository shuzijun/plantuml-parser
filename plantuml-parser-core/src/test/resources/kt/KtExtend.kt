package kt

open class Animal(val name: String) {
    open fun makeSound() {
        println("$name makes a sound")
    }
}

class Dog(name: String) : Animal(name) {
    override fun makeSound() {
        println("$name barks")
    }

    fun fetch() {
        println("$name is fetching a ball")
    }
}

fun main() {
    val animal = Animal("Generic Animal")
    val dog = Dog("Buddy")

    animal.makeSound() // 输出: Generic Animal makes a sound
    dog.makeSound()    // 输出: Buddy barks
    dog.fetch()         // 输出: Buddy is fetching a ball
}