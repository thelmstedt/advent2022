import java.io.File

fun main() {

    fun rays(
        grid: List<List<Int>>,
        x: Int,
        y: Int,
        excludeSelf: Boolean = true,
    ): List<List<Pair<Int, Int>>> {
        val height = grid.size
        val width = grid.first().size

        val d = if (excludeSelf) 1 else 0
        val up = (y - d downTo 0).map { x to it }
        val down = (y + d until height).map { x to it }
        val right = (x + d until width).map { it to y }
        val left = (x - d downTo 0).map { it to y }
        return listOf(up, left, down, right)
    }

    fun intGrid(text: String): List<List<Int>> = text.lineSequence()
        .map { row -> row.map { it.digitToInt() } }
        .toList()

    fun part1(file: File) {
        val grid = intGrid(file.readText())

        var visible = 0
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, tree ->
                if (rays(grid, x, y).any { it.all { (x, y) -> grid[y][x] < tree } }) {
                    visible += 1
                }
            }
        }
        println(visible)
    }

    fun rayScore(
        grid: List<List<Int>>,
        tree: Int,
        ray: List<Pair<Int, Int>>,
    ): Int {
        var score = 0
        ray.forEach { (x, y) ->
            score += 1
            if (grid[y][x] >= tree) return score
        }
        return score
    }

    fun part2(file: File) {
        val grid = intGrid(file.readText())

        val score = grid.flatMapIndexed { y, row ->
            row.mapIndexed { x, tree ->
                rays(grid, x, y)
                    .map { rayScore(grid, tree, it) }
                    .reduce { acc, i -> acc * i }
            }
        }.max()
        println(score)
    }


    part1(File("src", "Day08_test.txt"))
    part1(File("src", "Day08.txt"))
    part2(File("src", "Day08_test.txt"))
    part2(File("src", "Day08.txt"))

}
