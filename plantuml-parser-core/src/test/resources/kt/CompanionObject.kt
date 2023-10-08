package kt

class MyClass {
    companion object {
        const val staticField = "This is a static field"

        fun staticFunction() {
            println("This is a static function")
        }
    }
}

fun main() {
    println(MyClass.staticField) // 访问静态字段
    MyClass.staticFunction()      // 调用静态函数
}