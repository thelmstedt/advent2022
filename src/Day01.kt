import java.io.File

fun main() {
    fun elves(file: File) = file
        .readText().split("\n\n")
        .filter { it.isNotBlank() }
        .map { elf -> elf.split("\n").filter { it.isNotBlank() }.sumOf { it.toInt() } }
        .mapIndexed { index, i -> Pair(index + 1, i) }

    fun part1(file: File) = elves(file)
        .maxBy { it.second }

    fun part2(file: File) = elves(file)
        .sortedBy { it.second }
        .reversed()
        .take(3).sumOf { it.second }

    println(part1(File("src", "Day01_test.txt")))
    println(part1(File("src", "Day01.txt")))

    println(part2(File("src", "Day01_test.txt")))
    println(part2(File("src", "Day01.txt")))

}
