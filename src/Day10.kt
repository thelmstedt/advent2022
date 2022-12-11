import java.io.File

sealed class Instruction {
    object Noop : Instruction()
    data class Addx(val x: Int) : Instruction()
}


fun main() {


    fun part1(text: String) {
        val instructions = text.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                when (it) {
                    "noop" -> Instruction.Noop
                    else -> {
                        val (inst, arg) = it.split(" ")
                        when (inst) {
                            "addx" -> Instruction.Addx(arg.toInt())
                            else -> error(it)
                        }

                    }
                }
            }

        val placed = mutableMapOf<Int, Int>()
        var cycle = 1
        var X = 1
        instructions.forEach {
            when (it) {
                is Instruction.Addx -> {
                    placed[cycle] = X
                    placed[cycle + 1] = X
                    X += it.x
                    placed[cycle + 2] = X
                    cycle += 2
                }

                Instruction.Noop -> {
                    placed[cycle] = X
                    cycle += 1
                }
            }
        }
        println(placed)
        listOf(20, 60, 100, 140, 180, 220)
            .sumOf { placed[it]!! * it }
            .let { println(it) }
    }

    fun part2(text: String) {
        val instructions = text.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                when (it) {
                    "noop" -> Instruction.Noop
                    else -> {
                        val (inst, arg) = it.split(" ")
                        when (inst) {
                            "addx" -> Instruction.Addx(arg.toInt())
                            else -> error(it)
                        }

                    }
                }
            }

        val placed = mutableMapOf<Int, Int>()
        var cycle = 0
        var X = 1
        instructions.forEach {
            when (it) {
                is Instruction.Addx -> {
                    placed[cycle] = X
                    placed[cycle + 1] = X
                    X += it.x
                    placed[cycle + 2] = X
                    cycle += 2
                }

                Instruction.Noop -> {
                    placed[cycle] = X
                    cycle += 1
                }
            }
        }
        println(placed)
//        listOf(20, 60, 100, 140, 180, 220)
//            .sumOf { placed[it]!! * it }
//            .let { println(it) }


        val width = 40
        val height = 6

        val screen = " ".repeat(240).toMutableList()

        var sprite = (0..2)
        (0 until height).forEach { row ->
            placed.entries.forEach { e ->
                val (cycle, X) = e
                sprite = (X - 1..X + 1)

                if (sprite.contains(cycle - (width * row))) {
                    screen[cycle] = '#'
                }
            }
        }


        println(screen.chunked(40).map { it.joinToString("") }.joinToString("\n"))
    }


    val testInput = File("src", "Day10_test.txt").readText()
    val input = File("src", "Day10.txt").readText()

//    part1(testInput)
//    part1(input)
//
    part2(testInput)
    part2(input)

}
