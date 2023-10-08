package kt
enum class DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

fun main() {
    val today = DayOfWeek.WEDNESDAY

    // 使用 when 表达式匹配枚举值
    val message = when (today) {
        DayOfWeek.MONDAY -> "It's Monday!"
        DayOfWeek.WEDNESDAY -> "It's Wednesday!"
        else -> "It's not Monday or Wednesday."
    }

    println(message) // 输出: It's Wednesday!
}