import java.io.File

enum class Outcome(val score: Int) {
    WIN(6), DRAW(3), LOSS(0)
}

enum class RPS(val score: Int) {
    ROCK(1), PAPER(2), SCISSORS(3)
}

fun outcome(us: RPS, them: RPS): Outcome {
    return when (us) {
        RPS.ROCK -> when (them) {
            RPS.ROCK -> Outcome.DRAW
            RPS.PAPER -> Outcome.LOSS
            RPS.SCISSORS -> Outcome.WIN
        }

        RPS.PAPER -> when (them) {
            RPS.ROCK -> Outcome.WIN
            RPS.PAPER -> Outcome.DRAW
            RPS.SCISSORS -> Outcome.LOSS
        }

        RPS.SCISSORS -> when (them) {
            RPS.ROCK -> Outcome.LOSS
            RPS.PAPER -> Outcome.WIN
            RPS.SCISSORS -> Outcome.DRAW
        }
    }
}

fun main() {

    fun part1(file: File) = file
        .readText()
        .lineSequence()
        .map {
            val split = it.split(" ")
            val them = when (split[0]) {
                "A" -> RPS.ROCK
                "B" -> RPS.PAPER
                "C" -> RPS.SCISSORS
                else -> error("")
            }
            val us = when (split[1]) {
                "X" -> RPS.ROCK
                "Y" -> RPS.PAPER
                "Z" -> RPS.SCISSORS
                else -> error("")
            }
            val outcome = outcome(us, them)
            Pair(us.score, outcome.score)
        }
        .map { it.first + it.second }
        .toList()
        .sum()

    fun part2(file: File) = file
        .readText()
        .lineSequence()
        .map {
            val split = it.split(" ")
            val them = when (split[0]) {
                "A" -> RPS.ROCK
                "B" -> RPS.PAPER
                "C" -> RPS.SCISSORS
                else -> error("")
            }
            val outcome = when (split[1]) {
                "X" -> Outcome.LOSS
                "Y" -> Outcome.DRAW
                "Z" -> Outcome.WIN
                else -> error("")
            }
            val us = when (outcome) {
                Outcome.WIN -> when (them) {
                    RPS.ROCK -> RPS.PAPER
                    RPS.PAPER -> RPS.SCISSORS
                    RPS.SCISSORS -> RPS.ROCK
                }

                Outcome.DRAW -> them

                Outcome.LOSS -> when (them) {
                    RPS.ROCK ->  RPS.SCISSORS
                    RPS.PAPER ->  RPS.ROCK
                    RPS.SCISSORS ->  RPS.PAPER
                }
            }
            Pair(us.score, outcome.score)
        }
        .map { it.first + it.second }
        .toList()
        .sum()

    println(part1(File("src", "Day02_test.txt")))
    println(part1(File("src", "Day02.txt")))

    println(part2(File("src", "Day02_test.txt")))
    println(part2(File("src", "Day02.txt")))

}
