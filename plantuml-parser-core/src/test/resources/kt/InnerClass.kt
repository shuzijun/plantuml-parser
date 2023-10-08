package kt

class OuterClass {
    private val outerProperty = "Outer Property"

    class NestedClass {
        fun accessOuterProperty() {
            // 无法访问外部类的属性
            // println(outerProperty) // 错误：无法访问外部类的属性
        }
    }

    inner class InnerClass {
        fun accessOuterProperty() {
            // 可以访问外部类的属性
            println(outerProperty) // 可以访问外部类的属性
        }
    }
}

fun main() {
    val nested = OuterClass.NestedClass()
    val inner = OuterClass().InnerClass()

    nested.accessOuterProperty() // 无法访问外部类的属性
    inner.accessOuterProperty()   // 可以访问外部类的属性
}