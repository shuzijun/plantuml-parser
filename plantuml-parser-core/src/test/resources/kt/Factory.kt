package kt

interface Product {
    fun create()
}

class ConcreteProductA : Product {
    override fun create() {
        println("Product A is created")
    }
}

class ConcreteProductB : Product {
    override fun create() {
        println("Product B is created")
    }
}

object Factory {
    fun createProduct(type: String): Product {
        return when (type) {
            "A" -> ConcreteProductA()
            "B" -> ConcreteProductB()
            else -> throw IllegalArgumentException("Invalid product type")
        }
    }
}

fun main() {
    val productA = Factory.createProduct("A")
    val productB = Factory.createProduct("B")

    productA.create()
    productB.create()
}