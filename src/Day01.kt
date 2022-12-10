import java.io.File

fun main() {

    fun part1(text: String) {
        text.split("\n\n")
            .filter { it.isNotBlank() }
            .map { elf -> elf.lineSequence().filter { it.isNotBlank() }.sumOf { it.toInt() } }
            .mapIndexed { index, i -> Pair(index + 1, i) }
            .maxBy { it.second }
            .second
            .let { println(it) }
    }

    fun part2(text: String) {
        text.split("\n\n")
            .filter { it.isNotBlank() }
            .map { elf -> elf.lineSequence().filter { it.isNotBlank() }.sumOf { it.toInt() } }
            .mapIndexed { index, i -> Pair(index + 1, i) }
            .sortedByDescending { it.second }
            .take(3).sumOf { it.second }
            .let { println(it) }
    }

    val testInput = File("src", "Day01_test.txt").readText()
    val input = File("src", "Day01.txt").readText()

    part1(testInput)
    part1(input)

    part2(testInput)
    part2(input)

}
