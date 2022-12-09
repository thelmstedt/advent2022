import java.io.File
import kotlin.math.abs

fun main() {

    fun adjacent(head: Pair<Int, Int>, tail: Pair<Int, Int>): Boolean {
        return abs(head.first - tail.first) <= 1 && abs(head.second - tail.second) <= 1
    }

    fun move(
        dir: String,
        head: Pair<Int, Int>,
        tail: Pair<Int, Int>,
        moveHead: Boolean = true,
    ): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        var head1 = head
        var tail1 = tail
        when (dir) {
            "U" -> {
                if (moveHead) head1 = head1.first to head1.second - 1
                if (!adjacent(head1, tail1)) {
                    tail1 = head1.first to head1.second + 1
                }
            }

            "R" -> {
                if (moveHead) head1 = head1.first + 1 to head1.second
                if (!adjacent(head1, tail1)) {
                    tail1 = head1.first - 1 to head1.second
                }
            }

            "D" -> {
                if (moveHead) head1 = head1.first to head1.second + 1
                if (!adjacent(head1, tail1)) {
                    tail1 = head1.first to head1.second - 1
                }
            }

            "L" -> {
                if (moveHead) head1 = head1.first - 1 to head1.second
                if (!adjacent(head1, tail1)) {
                    tail1 = head1.first + 1 to head1.second
                }
            }

            else -> error(dir)
        }
        return Pair(head1, tail1)
    }


    fun part1(file: File) {

        val moves = file.readText()
            .lineSequence()
            .filter { it.isNotBlank() }
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }

        val heads = mutableSetOf<Pair<Int, Int>>() //x y
        val tails = mutableSetOf<Pair<Int, Int>>() //x y

        var head = 0 to 0 //x y
        var tail = 0 to 0 // x y
        heads.add(head)
        tails.add(tail)
        moves.forEach { (dir, c) ->
            (0 until c).forEach { _ ->
                val pair = move(dir, head, tail)
                head = pair.first
                tail = pair.second
                heads.add(head)
                tails.add(tail)
            }

        }

        println(tails.size)

    }

    fun display(
        tails: MutableList<Pair<Int, Int>>,
        start: Pair<Int, Int>,
        head: Pair<Int, Int>,
    ) {
        println("$head $tails")
        val map = (0..30).map { ".".repeat(30).toCharArray().map { it.toString() }.toMutableList() }.toMutableList()
        tails.forEachIndexed { index, pair ->
            val (x, y) = pair
            map[y][x] = index.toString()
        }
        map[start.second][start.first] = "S"
        map[head.second][head.first] = "H"
        map.forEach {
            println(it.joinToString(""))
        }
        println("------------")
    }

    fun part2(text: String) {

        val moves = text
            .lineSequence()
            .filter { it.isNotBlank() }
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }

        val headPos = mutableSetOf<Pair<Int, Int>>() //x y
        val tailPos = mutableSetOf<Pair<Int, Int>>() //x y

        var head = 10 to 15 //x y
        val start = head
        val tails = (0 until 10).map { head }.toMutableList() // x y
        headPos.add(head)
        tailPos.add(head)
        moves.forEach { (dir, c) ->
            (0 until c).forEach { _ ->
                val (head1, tail1) = move(dir, head, tails[0])
                head = head1
                tails[0] = tail1
                headPos.add(head)
                tails.forEach { tailPos.add(it) }

                display(tails, start, head)

                (0 until 10).forEach { i ->
                    val first = tails[i]
                    val second = tails.getOrNull(i + 1)

                    if (second != null && !adjacent(first, second)) {
                        val (_, second1) = move(dir, first, second, false)
                        tails[i + 1] = second1

                        headPos.add(head)
                        tails.forEach { tailPos.add(it) }
                    }
                }


            }

            println(tailPos.size)
        }


    }

//    part1(File("src", "Day09_test.txt"))
//    part1(File("src", "Day09.txt"))
//    part2(File("src", "Day09_test.txt").readText())
//    part2(File("src", "Day09.txt").readText())

    part2(
        """
    R 5
    U 2
    """.trimIndent()
    )

//    part2("""R 5
//U 8
//L 8
//D 3
//R 17
//D 10
//L 25
//U 20
//""")

}
