import java.io.File


fun main() {

    fun part1(file: File) = file
        .readText()
        .lineSequence()
        .map {
            when (it) {
                "A X" -> 3 + 1
                "A Y" -> 6 + 2
                "A Z" -> 0 + 3
                "B X" -> 0 + 1
                "B Y" -> 3 + 2
                "B Z" -> 6 + 3
                "C X" -> 6 + 1
                "C Y" -> 0 + 2
                "C Z" -> 3 + 3
                else -> error(it)
            }
        }
        .sum()

    fun part2(file: File) = file
        .readText()
        .lineSequence()
        .map {
            when (it) {
                "A X" -> 3 + 0
                "A Y" -> 1 + 3
                "A Z" -> 2 + 6
                "B X" -> 1 + 0
                "B Y" -> 2 + 3
                "B Z" -> 3 + 6
                "C X" -> 2 + 0
                "C Y" -> 3 + 3
                "C Z" -> 1 + 6
                else -> error(it)
            }
        }
        .sum()

    println(part1(File("src", "Day02_test.txt")))
    println(part1(File("src", "Day02.txt")))

    println(part2(File("src", "Day02_test.txt")))
    println(part2(File("src", "Day02.txt")))

}
