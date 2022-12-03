import java.io.File


fun main() {


    fun part1(file: File): Int {
        val priorities = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val priorityMap = priorities.mapIndexed { index, c -> c to index + 1 }.toMap()
        return file
            .readText()
            .lineSequence()
            .filter { it.isNotBlank() }
            .map { it.chunked(it.length / 2) }
            .map { (l, r) -> l.toCharArray().toSet().intersect(r.toCharArray().toSet()) }
            .map { it.map { priorityMap[it]!! } }
            .flatMap { it }
            .sum()
    }


    fun part2(file: File): Int {
        val priorities = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val priorityMap = priorities.mapIndexed { index, c -> c to index + 1 }.toMap()
        return file
            .readText()
            .lineSequence()
            .filter { it.isNotBlank() }
            .chunked(3)
            .map { (l, m, r) ->
                val ls = l.toCharArray().toSet()
                val rs = r.toCharArray().toSet()
                val ms = m.toCharArray().toSet()
                ls.intersect(rs).intersect(ms)
            }
            .map { it.map { priorityMap[it]!! } }
            .flatMap { it }
            .sum()
    }


    println(part1(File("src", "Day03_test.txt")))
    println(part1(File("src", "Day03.txt")))

    println(part2(File("src", "Day03_test.txt")))
    println(part2(File("src", "Day03.txt")))

}
