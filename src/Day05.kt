import java.io.File
import java.util.Stack

fun main() {


    fun parseCommands(moves: String): List<List<Int>> {
        val commands = moves.lineSequence()
            .filter { it.isNotBlank() }
            .map { move ->
                move.replace("move ", "")
                    .replace("from ", "")
                    .replace("to ", "")
                    .split(" ")
                    .map { it.toInt() }
            }.toList()
        return commands
    }

    fun stacks(map: String): MutableList<Stack<String>> {
        val maplines = map.lines()
        val bucketCount = maplines.last().replace(" ", "").length

        val initialState = maplines
            .dropLast(1)
            .filter { it.isNotBlank() }
            .map { line ->
                val chunked = line.chunked(4)
                val cc = (0 until bucketCount).map {
                    val trim = chunked.getOrNull(it)?.trim()?.replace("[", "")?.replace("]", "")
                    if (trim?.isBlank() == true) null else trim
                }.toMutableList()
                while (cc.size < bucketCount) {
                    cc.add(null)
                }
                cc
            }.toList()
        val columns = mutableListOf<Stack<String>>()
        (0 until bucketCount).forEach { idx ->
            val col = Stack<String>()
            columns.add(col)
        }
        (0 until bucketCount).forEach { idx ->
            initialState.reversed().forEach { state ->
                state[idx]?.let { columns[idx].push(it) }
            }
        }
        return columns
    }

    fun part1(file: File): Any {
        val text = file.readText()

        val (map, moves) = text.split("\n\n")

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
        val text = file.readText()

        val (map, moves) = text.split("\n\n")

        println(map)
        println(moves)

        val commands = parseCommands(moves)
        val columns = stacks(map)

        columns.forEach {
            println(it)
        }

        commands.forEach { (move, from, to) ->
            val popped = mutableListOf<String>()
            (0 until move).forEach { _ ->
                popped.add(columns[from - 1].pop())
            }
            println("XXX $popped from $from to $to")
            popped.reversed().forEach {
                columns[to - 1].push(it)

            }

            columns.forEach {
                println(it)
            }

            println()
        }

        columns.forEach {
            println(it)
        }

        var answer = ""
        columns.forEach {
            answer += it.pop()
        }
        return answer
    }


//    println(part1(File("src", "Day05_test.txt")))
//    println(part1(File("src", "Day05.txt")))
    println(part2(File("src", "Day05_test.txt")))
    println(part2(File("src", "Day05.txt")))

}

