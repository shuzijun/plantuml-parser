class KotlinKeywordsExample {
    val myVal: Int = 42
    var myVar: String = "Hello"
    lateinit var myLateInitVar: String

    fun myFunction(): String? {
        return null
    }

    data class MyDataClass(val name: String)
    open class MyBaseClass
    interface MyInterface
    enum class MyEnum { ONE, TWO, THREE }

    class MyDerivedClass : MyBaseClass(), MyInterface {
        override fun toString(): String {
            return "Derived Class"
        }
    }

    companion object {
        const val myConstVal: Double = 3.14
    }
}