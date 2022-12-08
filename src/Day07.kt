import java.io.File

sealed class Entry(open val name: String) {
    data class File(override val name: String, val size: Long) : Entry(name)
    data class Dir(
        override val name: String,
        val prev: Dir? = null,
        var size: Long? = null,
        val children: MutableList<Entry> = mutableListOf(),
    ) : Entry(name)
}

fun size(x: Entry): Long = when (x) {
    is Entry.Dir -> {
        val sumOf = x.children.sumOf { size(it) }
        x.size = sumOf
        sumOf
    }

    is Entry.File -> x.size
}

fun dirs(x: Entry, xs: MutableList<Entry.Dir>) {
    when (x) {
        is Entry.Dir -> {
            xs.add(x)
            x.children.forEach {
                dirs(it, xs)
            }
        }

        is Entry.File -> {

        }
    }
}

fun printDir(x: Entry, tab: String = "", minSize: Long = 0) {
    when (x) {
        is Entry.Dir -> {
            println("${tab}- ${x.name} (dir, size=${x.size}")
            x.children.forEach {
                printDir(it, "$tab ", minSize)
            }
        }

        is Entry.File -> {
            println("$tab- ${x.name} (file, size=${x.size})")
        }
    }
}

fun sumAtMost(x: Entry, n: Long = 100000): Long {
    val xs = mutableListOf<Entry.Dir>()
    dirs(x, xs)
    return xs.filter { size(it) <= n }.sumOf { size(it) }
}


fun main() {


    fun getOutput(lines: List<String>, cursor: Int): List<String> {
        val outlines = lines.subList(cursor, lines.size).takeWhile { !it.startsWith("$") }
        return outlines
    }

    fun parseDirs(text: String): Entry.Dir {
        var cursor = 0

        var currentDir: Entry.Dir = Entry.Dir("/")
        val root = currentDir


        val lines = text.lines().filter { it.isNotBlank() }.drop(1)
        while (cursor < lines.size) {
            val line = lines[cursor]
            cursor += 1

            var cmd: String? = null
            var args: String? = null
            if (line.startsWith("$")) {
                val split = line.drop(2).split(" ")
                cmd = split[0]
                args = split.getOrNull(1)

                when (cmd) {
                    "cd" -> {
                        if (args == "..") {
                            currentDir = currentDir.prev!!
                        } else {
                            currentDir =
                                (currentDir.children.find { it.name == args }
                                    ?: error("Can't find $args in $currentDir")) as Entry.Dir
                        }

                    }

                    "ls" -> {
                        val output = getOutput(lines, cursor)
                        cursor += output.size
                        output.forEach { it ->
                            val split = it.split(" ")
                            if (it.matches("[0-9].*".toRegex())) {
                                currentDir.children.add(Entry.File(split[1], split[0].toLong()))
                            } else {
                                if (split[0] != "dir") error("What? $it")
                                val element = Entry.Dir(split[1], prev = currentDir)
                                currentDir.children.add(element)
                            }

                        }
                    }
                }
            }
        }
        return root
    }

    fun part1(text: String) {
        val root = parseDirs(text)
        println(printDir(root))
        println(sumAtMost(root))
    }

    fun part2(text: String) {
        val root = parseDirs(text)
        size(root)


        val total = 70000000L
        val desired = 30000000L
        val current = root.size!!
        val unused = total - current
        var required = desired - unused
        println(current)
        println(required)
        printDir(root, "")



        val allDirs = mutableListOf<Entry.Dir>()
        dirs(root, allDirs)

//        print(root)

        val sortedBy = allDirs.sortedBy { it.size!! }
        sortedBy.forEach {
            val size = it.size!!
            println("${it.name} $size ${size >= required} - from ${required - size}")
        }
        println(sortedBy.first { size(it) >= required }.name)
        println(sortedBy.first { size(it) >= required }.size)
    }

    val testContent = File("src", "Day07_test.txt").readText()
    val content = File("src", "Day07.txt").readText()
    part2(testContent)
    part2(content)
}


//10389918
//9290252