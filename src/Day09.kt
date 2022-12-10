import java.io.File
import kotlin.math.abs

enum class Cardinal {
    N, NE, E, SE, S, SW, W, NW
}

typealias Point = Pair<Int, Int>

fun move(p: Point, dir: Cardinal): Pair<Int, Int> {
    return when (dir) {
        Cardinal.N -> p.first to p.second - 1
        Cardinal.NE -> p.first + 1 to p.second - 1
        Cardinal.E -> p.first + 1 to p.second
        Cardinal.SE -> p.first + 1 to p.second + 1
        Cardinal.S -> p.first to p.second + 1
        Cardinal.SW -> p.first - 1 to p.second + 1
        Cardinal.W -> p.first - 1 to p.second
        Cardinal.NW -> p.first - 1 to p.second - 1
    }
}

fun adjacent(p1: Point, p2: Point): Boolean {
    val deltaX = abs(p1.first - p2.first)
    val deltaY = abs(p1.second - p2.second)
    return deltaX <= 1 && deltaY <= 1
}

fun relativeMove(head: Point, tail: Point, dir: String): Pair<Point, Point> {
    val head1 = straight(dir, head)
    return if (adjacent(head1, tail)) {
        head1 to tail
    } else {
        head1 to tail(head1, tail)
    }
}

private fun tail(relative: Point, tail: Point): Point {
    val isWest = relative.first > tail.first
    val isNorth = relative.second > tail.second
    val NS = tail.first == relative.first
    val EW = tail.second == relative.second
    val tail1: Point = if (NS || EW) {
        straight(
            if (NS) {
                if (isNorth) {
                    "D"
                } else {
                    "U"
                }
            } else if (EW) {
                if (isWest) {
                    "R"
                } else {
                    "L"
                }
            } else {
                error("WHAT")
            }, tail
        )
    } else {
        // diagonal
        move(
            tail, when (isWest to isNorth) {
                (true to true) -> Cardinal.SE
                (false to false) -> Cardinal.NW
                (true to false) -> Cardinal.NE
                (false to true) -> Cardinal.SW
                else -> error("Bad")
            }
        )
    }
    return tail1
}


private fun straight(dir: String, head: Point): Pair<Int, Int> {
    val head1 = when (dir) {
        "U" -> move(head, Cardinal.N)
        "R" -> move(head, Cardinal.E)
        "D" -> move(head, Cardinal.S)
        "L" -> move(head, Cardinal.W)
        else -> error(dir)
    }
    return head1
}



fun main() {

    fun part1(text: String) {

        val moves = text
            .lineSequence()
            .filter { it.isNotBlank() }
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }

        val tailPos = mutableSetOf<Pair<Int, Int>>() //x y

        val rope = mutableListOf(0 to 6, 0 to 6)
        tailPos.add(rope.last())
        moves.forEach { (dir, c) ->
            (0 until c).forEach { _ ->
                val (head1, tail1) = relativeMove(rope[0], rope[1], dir)
                rope[0] = head1
                rope[1] = tail1
                tailPos.add(rope.last())
            }
        }

        println(tailPos.size)

    }

    fun part2(text: String) {

        val moves = text
            .lineSequence()
            .filter { it.isNotBlank() }
            .map { it.split(" ") }
            .map {
                val dir = it[0]
                dir to it[1].toInt()
            }

        val tailPos = mutableSetOf<Pair<Int, Int>>() //x y

        val start = 13 to 16
        val rope = mutableListOf(start)
        (0 until 9).forEach { _ -> rope.add(start) }

        tailPos.add(rope.last())
        moves.forEach { (dir, c) ->
            (0 until c).forEach { _ ->
                val (head1, tail1) = relativeMove(rope[0], rope[1], dir)
                rope[0] = head1
                rope[1] = tail1
                (2 until 10).forEach { i ->
                    val head = rope[i - 1]
                    val tail = rope[i]
                    if (!adjacent(head, tail)) {
                        rope[i] = tail(head, tail)
                    }
                }
                tailPos.add(rope.last())
            }
//            display(start, rope)
        }

        println(tailPos.size)

    }

    val testInput = File("src", "Day09_test.txt").readText()
    val input = File("src", "Day09.txt").readText()
    part1(testInput)
    part1(input)
    part2(
        """
R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20
    """.trimIndent()
    )
    part2(input)


}



fun display(
    start: Pair<Int, Int>,
    rope: List<Pair<Int, Int>>,
) {
    if (false) return
    println("$rope")
    val size = "..........................".length * 2
    val map = (0..size).map { ".".repeat(size).toCharArray().map { it.toString() }.toMutableList() }.toMutableList()
    rope.forEachIndexed { index, pair ->
        val (x, y) = pair
        map[y][x] = if (index == 0) "H" else index.toString()
    }
    map[start.second][start.first] = "S"
    map.forEach {
        println(it.joinToString(""))
    }
    println("------------")
}

fun displayPos(
    start: Pair<Int, Int>,
    rope: List<Pair<Int, Int>>,
) {
    if (false) return
    println("$rope")
    val size = "..........................".length * 2
    val map = (0..size).map { ".".repeat(size).toCharArray().map { it.toString() }.toMutableList() }.toMutableList()
    rope.forEachIndexed { index, pair ->
        val (x, y) = pair
        map[y][x] = "#"
    }
    map[start.second][start.first] = "S"
    map.forEach {
        println(it.joinToString(""))
    }
    println("------------")
}