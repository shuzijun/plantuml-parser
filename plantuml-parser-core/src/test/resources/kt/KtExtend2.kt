package kt2

open class Animal(val name: String) {
    open fun makeSound() {
        println("$name makes a sound")
    }
}