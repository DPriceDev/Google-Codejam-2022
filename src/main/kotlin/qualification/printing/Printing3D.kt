package qualification.printing

data class Ink(val c: Int = 0, val y: Int = 0, val n: Int = 0, val k: Int = 0)

fun main(args: Array<String>) {
    val caseCount = readLine()?.toInt() ?: error("Case count not found!")

    val coloursRegex = """([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)""".toRegex()

    repeat(caseCount) { caseIndex ->
        print("Case #${ caseIndex + 1 }: ")

        val inkLevels = getInkLevels(coloursRegex)
        val availableLevels = getMinimumAvailableLevels(inkLevels)
        val requiredLevels = getLevelsRequiredToPrint(availableLevels)

        requiredLevels?.apply { println("$c $y $n $k") } ?: println("IMPOSSIBLE")
    }
}

private fun getInkLevels(coloursRegex: Regex) = (0 until 3).map { index ->
    val printerLine = readLine() ?: error("Printer $index not found")
    val (c, y, n, k) = coloursRegex.find(printerLine)?.destructured ?: error("Cannot parse printer $index")
    Ink(c.toInt(), y.toInt(), n.toInt(), k.toInt())
}

fun getMinimumAvailableLevels(inkLevels: List<Ink>)= inkLevels.reduce { running, next ->
    Ink(
        minOf(running.c, next.c),
        minOf(running.y, next.y),
        minOf(running.n, next.n),
        minOf(running.k, next.k),
    )
}

fun getLevelsRequiredToPrint(levels: Ink, target: Int = 1_000_000): Ink? {
    listOf(levels.c, levels.y, levels.n, levels.k).foldIndexed(target) { index, running, next ->
        val total = running - next

        if(total <= 0) {
            return Ink(
                getRequiredLevelForIndex(index, 0, levels.c, total),
                getRequiredLevelForIndex(index, 1, levels.y, total),
                getRequiredLevelForIndex(index, 2, levels.n, total),
                getRequiredLevelForIndex(index, 3, levels.k, total)
            )
        }
        total
    }
    return null
}

fun getRequiredLevelForIndex(index: Int, levelIndex: Int, level: Int, total: Int): Int = when {
    levelIndex < index -> level
    levelIndex == index -> level + total
    levelIndex > index -> 0
    else -> error("Invalid level index")
}