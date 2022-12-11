import java.io.File

fun main() {

    data class Monkey(
        val id: Int,
        var items: MutableList<Long>,
        val operation: List<String>,
        val div: Long,
        val ifTrue: Int,
        val ifFalse: Int,
        var inspectionCount: Long = 0,
    )

    fun monkies(text: String): List<Monkey> {
        val monkeys = text
            .split("\n\n")
            .filter { it.isNotBlank() }
            .map { spec ->
                val lines = spec.lines()

                val monkeyId = "[0-9]+".toRegex().allMatches(lines[0]).first().toInt()
                val worryLevels = "[0-9]+".toRegex().allMatches(lines[1]).map { it.toLong() }.toMutableList()
                val operation = lines[2].split("=")[1].trim().split(" ")
                val testDiv = "[0-9]+".toRegex().allMatches(lines[3]).first().toLong()
                val ifTrue = "[0-9]+".toRegex().allMatches(lines[4]).first().toInt()
                val ifFalse = "[0-9]+".toRegex().allMatches(lines[5]).first().toInt()

                Monkey(
                    monkeyId,
                    worryLevels,
                    operation,
                    testDiv,
                    ifTrue,
                    ifFalse
                )

            }
        return monkeys
    }

    fun part1(text: String) {
        val monkeys = monkies(text)

        val monkeyLookup = monkeys.associateBy { it.id }

        (0 until 20).forEach {
            monkeys.forEach { monkey ->
//                println("Monkey ${monkey.id}:")
                monkey.items.forEach {
//                    println("\tMonkey inspects an item with a worry level of ${it}.")
                    val op = monkey.operation
                    val op0 = if (op[0] == "old") it else op[0].toLong()
                    val op2 = if (op[2] == "old") it else op[2].toLong()
                    val new1 = when (op[1]) {
                        "+" -> op0 + op2
                        "*" -> op0 * op2
                        else -> error("$op")
                    }
                    val new = new1
//                    println("\t\tWorry level increases by X to ${new}.")
                    val bored = new / 3L
//                    println("\t\tMonkey gets bored with item. Worry level is divided by 3 to ${bored}")
                    if (bored % monkey.div == 0L) {
//                        println("\t\tCurrent worry level is divisible by ${monkey.div}.")
//                        println("\t\tItem with worry level $bored is thrown to monkey ${monkey.ifTrue}.")
                        monkeyLookup[monkey.ifTrue]!!.items.add(bored)
                    } else {
//                        println("\t\tCurrent worry level is not divisible by ${monkey.div}.")
//                        println("\t\tItem with worry level $bored is thrown to monkey ${monkey.ifFalse}.")
                        monkeyLookup[monkey.ifFalse]!!.items.add(bored)
                    }
                    monkey.inspectionCount += 1
                }
                monkey.items.clear()
            }
//            println("After round ${it}, the monkeys are holding items with these worry levels:")
//            monkeys.forEach { m ->
//                println("Monkey ${m.id}: ${m.items.joinToString(", ")}")
//            }
//            println()
        }

        monkeys.forEach {
            println("monkey ${it.id} inspected ${it.inspectionCount}")
        }

        println(monkeys.map {
            it.inspectionCount
        }.sortedDescending().take(2).reduce({ acc: Long, i: Long -> acc * i }))
    }


    fun part2(text: String) {
        val monkeys = monkies(text)

        val monkeyLookup = monkeys.associateBy { it.id }

        // chinese remainder theorem
        val gcm = monkeys.map { it.div }.reduce { acc: Long, l: Long -> acc * l }

        (0 until 10000).forEach { it ->
            monkeys.forEach { m ->
                m.items.forEach {
                    val op = m.operation
                    val op0 = if (op[0] == "old") it % gcm else op[0].toLong() % gcm
                    val op2 = if (op[2] == "old") it % gcm else op[2].toLong() % gcm
                    val new = when (op[1]) {
                        "+" -> op0 + op2
                        "*" -> op0 * op2
                        else -> error("$op")
                    }
                    if (new % m.div == 0L) {
                        monkeyLookup[m.ifTrue]!!.items.add(new)
                    } else {
                        monkeyLookup[m.ifFalse]!!.items.add(new)
                    }
                    m.inspectionCount += 1
                }
                m.items.clear()
            }
            if (listOf(1, 20, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000).contains(it + 1)) {
                println("After round ${it + 1}, the monkeys are holding items with these worry levels:")
                monkeys.forEach {
                    println("monkey ${it.id} inspected ${it.inspectionCount}")
                }

                println(monkeys.map {
                    it.inspectionCount
                }.sortedDescending().take(2).reduce({ acc: Long, i: Long -> acc * i }))
            }

        }

        println("After round 10000, the monkeys are holding items with these worry levels:")
        monkeys.forEach {
            println("monkey ${it.id} inspected ${it.inspectionCount}")
        }

        println(monkeys.map {
            it.inspectionCount
        }.sortedDescending().take(2).reduce({ acc: Long, i: Long -> acc * i }))
    }

    val testInput = File("src", "Day11_test.txt").readText()
    val input = File("src", "Day11.txt").readText()

//    part1(testInput)
//    part1(input)

    part2(testInput)
    part2(input)

}
