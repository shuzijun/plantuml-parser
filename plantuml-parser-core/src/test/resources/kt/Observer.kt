package kt
interface Observer {
    fun update(message: String)
}

class ConcreteObserver(private val name: String) : Observer {
    override fun update(message: String) {
        println("$name received message: $message")
    }
}

class Subject {
    private val observers = mutableListOf<Observer>()

    fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    fun notifyObservers(message: String) {
        observers.forEach { it.update(message) }
    }
}

fun main() {
    val subject = Subject()
    val observer1 = ConcreteObserver("Observer 1")
    val observer2 = ConcreteObserver("Observer 2")

    subject.addObserver(observer1)
    subject.addObserver(observer2)

    subject.notifyObservers("Hello, observers!")
}