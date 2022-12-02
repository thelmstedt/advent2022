import java.io.File

enum class Cond {
    WIN, DRAW, LOSS
}

fun main() {

    fun part1(file: File) = file
        .readText()
        .lineSequence()
        .map {
            val split = it.split(" ")

            val rock = Triple("A", "X", 1)
            val paper = Triple("B", "Y", 2)
            val scissors = Triple("C", "Z", 3)

            val us = when (split[1]) {
                "X" -> rock
                "Y" -> paper
                "Z" -> scissors
                else -> error("")
            }
            val them = when (split[0]) {
                "A" -> rock
                "B" -> paper
                "C" -> scissors
                else -> error("")
            }
            var winScore = 0
            if (them == us) {
                winScore = 3
            }
            when (us) {
                rock -> if (them == scissors) winScore = 6
                paper -> if (them == rock) winScore = 6
                scissors -> if (them == paper) winScore = 6
                else -> error("")
            }
            Pair(us.third, winScore)
        }
        .map { it.first + it.second }
        .toList()
        .sum()

    fun part2(file: File) = file
        .readText()
        .lineSequence()
        .map {
            val split = it.split(" ")

            val rock = Triple("A", "X", 1)
            val paper = Triple("B", "Y", 2)
            val scissors = Triple("C", "Z", 3)

            val cond = when (split[1]) {
                "X" -> Cond.LOSS
                "Y" -> Cond.DRAW
                "Z" -> Cond.WIN
                else -> error("")
            }
            val them = when (split[0]) {
                "A" -> rock
                "B" -> paper
                "C" -> scissors
                else -> error("")
            }
            var us = rock
            us = when (them) {
                rock -> {
                    when(cond) {
                        Cond.WIN -> paper
                        Cond.DRAW -> rock
                        Cond.LOSS -> scissors
                    }
                }
                paper -> {
                    when(cond) {
                        Cond.WIN -> scissors
                        Cond.DRAW -> paper
                        Cond.LOSS -> rock
                    }
                }
                scissors -> {
                    when(cond) {
                        Cond.WIN -> rock
                        Cond.DRAW -> scissors
                        Cond.LOSS -> paper
                    }
                }

                else -> error("what")
            }


            val winScore = when(cond) {
                Cond.WIN -> 6
                Cond.DRAW -> 3
                Cond.LOSS -> 0
            }
            Pair(us.third, winScore)
        }
        .map { it.first + it.second }
        .toList()
        .sum()

    println(part1(File("src", "Day02_test.txt")))
    println(part1(File("src", "Day02.txt")))

    println(part2(File("src", "Day02_test.txt")))
    println(part2(File("src", "Day02.txt")))

}
