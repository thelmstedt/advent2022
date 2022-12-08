import java.io.File

fun main() {


    fun visible(trees: List<Int>, tree: Int): Boolean {
        return !trees.any { it >= tree }
    }

    fun visible2(trees: List<Int>, tree: Int): Int {
        var c = 0
        trees.forEach {
            c += 1
            if (it >= tree) return c
        }
        return c
    }

    fun part1(file: File) {
        val map = file.readText()

        val lines = map.lines()
        val height = lines.size
        val width = lines.first().length

        val lookup = mutableMapOf<Pair<Int, Int>, Int>()

        var visible = 0
        map
            .lineSequence()
            .forEachIndexed { y, row ->
                row.forEachIndexed { x, t ->
                    lookup[x to y] = t.digitToInt()
                }
            }

        lookup.entries.forEach { (pos, tree) ->
            val x = pos.first
            val y = pos.second
            val up = (0 until y).filter { pos != x to it }.map { lookup[x to it]!! }
            val down = (y until height).filter { pos != x to it }.map { lookup[x to it]!! }
            val right = (x until width).filter { pos != it to y }.map { lookup[it to y]!! }
            val left = (0 until x).filter { pos != it to y }.map { lookup[it to y]!! }
//            println("$x $y $tree - UP $up - ${visible(up, tree)}")
//            println("$x $y $tree - DOWN $down - ${visible(down, tree)} ")
//            println("$x $y $tree - RIGHT $right - ${visible(right, tree)} ")
//            println("$x $y $tree - LEFT $left - ${visible(left, tree)} ")

            if (visible(up, tree) || visible(down, tree) || visible(right, tree) || visible(left, tree)) {
                visible += 1
            }
        }
        print(visible)
    }

    fun part2(file: File) {
        val map = file.readText()

        val lines = map.lines()
        val height = lines.size
        val width = lines.first().length

        val lookup = mutableMapOf<Pair<Int, Int>, Int>()

        var visible = 0
        map
            .lineSequence()
            .forEachIndexed { y, row ->
                row.forEachIndexed { x, t ->
                    lookup[x to y] = t.digitToInt()
                }
            }

        val xs = mutableListOf<Int>()
        lookup.entries.forEach { (pos, tree) ->
            val x = pos.first
            val y = pos.second
            val up = (0 until y).reversed().filter { pos != x to it }.map { lookup[x to it]!! }
            val down = (y until height).filter { pos != x to it }.map { lookup[x to it]!! }
            val right = (x until width).filter { pos != it to y }.map { lookup[it to y]!! }
            val left = (0 until x).reversed().filter { pos != it to y }.map { lookup[it to y]!! }

//            println("$x $y $tree - UP $up - ${visible(up, tree)}- ${visible2(up, tree)}")
//            println("$x $y $tree - DOWN $down - ${visible(down, tree)} - ${visible2(down, tree)} ")
//            println("$x $y $tree - RIGHT $right - ${visible(right, tree)} - ${visible2(right, tree)} ")
//            println("$x $y $tree - LEFT $left - ${visible(left, tree)} - ${visible2(left, tree)} ")

            if (visible(up, tree) || visible(down, tree) || visible(right, tree) || visible(left, tree)) {
                xs.add(visible2(up, tree) * visible2(down, tree) * visible2(left, tree) * visible2(right, tree))
            }
        }
        println(xs.max())
    }


//    part1(File("src", "Day08_test.txt"))
//    part1(File("src", "Day08.txt"))
    part2(File("src", "Day08_test.txt"))
    part2(File("src", "Day08.txt"))

}
