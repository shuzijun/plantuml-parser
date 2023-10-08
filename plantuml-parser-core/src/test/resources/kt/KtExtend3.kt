package kt3
import kt2.Animal as Animal2
class Dog(name: String) : Animal2(name) {
    override fun makeSound() {
        println("$name barks")
    }

    fun fetch() {
        println("$name is fetching a ball")
    }
}

fun main() {
    val animal = Animal2("Generic Animal")
    val dog = Dog("Buddy")

    animal.makeSound() // 输出: Generic Animal makes a sound
    dog.makeSound()    // 输出: Buddy barks
    dog.fetch()         // 输出: Buddy is fetching a ball
}