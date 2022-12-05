import java.io.File
import java.util.*

fun main() {


    fun parseCommands(moves: String): List<List<Int>> {
        val commands = moves.lineSequence()
            .filter { it.isNotBlank() }
            .map { move ->
                allMatches("[0-9]+".toRegex(), move).map { it.toInt() }
            }.toList()
        return commands
    }

    fun stacks(map: String): MutableList<Stack<String>> {
        val bucketCount = map.lines().last().replace(" ", "").length
        val columns = mutableListOf<Stack<String>>()
        (0 until bucketCount).forEach { idx ->
            val col = Stack<String>()
            columns.add(col)
        }
        val mapLines = map.lines()
            .dropLast(1)
            .filter { it.isNotBlank() }
            .map { it.chunked(4) }

        (0 until bucketCount).forEach { idx ->
            mapLines.forEach { line ->
                val box = line.getOrNull(idx)
                if (!box.isNullOrBlank()) {
                    columns[idx].push(box.replace("[", "").replace("]", "").trim())
                }
            }
        }
        columns.forEach {
            it.reverse()
        }
        return columns
    }

    fun part1(file: File): Any {
        val (map, moves) = file.readText().split("\n\n")

        println(map)
        println(moves)

        val commands = parseCommands(moves)
        val stacks = stacks(map)

        stacks.forEach {
            println(it)
        }

        commands.forEach { (move, from, to) ->
            (0 until move).forEach { _ ->
                val pop = stacks[from - 1].pop()
                println("$pop from $from to $to")
                stacks[to - 1].push(pop)

                stacks.forEach {
                    println(it)
                }
                println()
            }
        }

        stacks.forEach {
            println(it)
        }

        var answer = ""
        stacks.forEach {
            answer += it.pop()
        }
        return answer
    }

    fun part2(file: File): Any {
        val (map, moves) = file.readText().split("\n\n")

        println(map)
        println(moves)

        val commands = parseCommands(moves)
        val stacks = stacks(map)

        stacks.forEach {
            println(it)
        }

        commands.forEach { (move, from, to) ->
            val popped = mutableListOf<String>()
            (0 until move).forEach { _ ->
                popped.add(stacks[from - 1].pop())
            }
            println("XXX $popped from $from to $to")
            popped.reversed().forEach {
                stacks[to - 1].push(it)

            }

            stacks.forEach {
                println(it)
            }

            println()
        }

        stacks.forEach {
            println(it)
        }

        var answer = ""
        stacks.forEach {
            answer += it.pop()
        }
        return answer
    }

    println(part1(File("src", "Day05_test.txt")))
    println(part1(File("src", "Day05.txt")))
    println(part2(File("src", "Day05_test.txt")))
    println(part2(File("src", "Day05.txt")))
}


