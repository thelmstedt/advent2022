import java.io.File

typealias Range = Pair<Int, Int>

fun main() {


    fun range(l: String): Range {
        val (l1, l2) = l.split("-")
        return Pair(l1.toInt(), l2.toInt())
    }

    fun fullyContains(r1: Range, r2: Range): Boolean {
        val rr1 = (r1.first..r1.second).toSet()
        val rr2 = (r2.first..r2.second).toSet()
        return rr1.minus(rr2).isEmpty() || rr2.minus(rr1).isEmpty()
    }

    fun overlap(r1: Range, r2: Range): Boolean {
        val rr1 = (r1.first..r1.second).toSet()
        val rr2 = (r2.first..r2.second).toSet()
        return rr1.intersect(rr2).isNotEmpty()
    }

    fun part1(file: File) = file
        .readText()
        .lineSequence()
        .filter { it.isNotBlank() }
        .filter {
            val (l, r) = it.split(",")
            fullyContains(range(l), range(r))
        }
        .count()

    fun part2(file: File) = file
        .readText()
        .lineSequence()
        .filter { it.isNotBlank() }
        .filter {
            val (l, r) = it.split(",")
            overlap(range(l), range(r))
        }
        .count()


    println(part1(File("src", "Day04_test.txt")))
    println(part1(File("src", "Day04.txt")))
    println(part2(File("src", "Day04_test.txt")))
    println(part2(File("src", "Day04.txt")))

}
