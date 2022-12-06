import java.io.File

fun main() {
    fun part1(text: String, n: Int) = text.windowed(n).indexOfFirst { it.toSet().size == n } + n

    val input = File("src", "Day06.txt").readText()
    println(part1(input, 4))
    println(part1(input, 14))
}


