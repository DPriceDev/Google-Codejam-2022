package qualification.punchcard

data class RowDefinition(val divider: String, val body: String)

fun main(args: Array<String>) {
    val rowColumnRegex = """([0-9]+) ([0-9]+)""".toRegex()

    val caseCount = readLine()?.toInt() ?: error("Case count not found!")

    repeat(caseCount) { index ->
        println("Case #${ index + 1 }:")
        val line = readLine() ?: error("Line $index not found")
        val (rowCount, columnCount) = rowColumnRegex.find(line)?.destructured ?: error("Failed to parse line $index")

        val definition = getRowDefinition(columnCount.toInt())

        printFirstRow(rowCount.toInt(), definition)
        printRemainingRows(rowCount.toInt(), definition)
    }
}

private fun getRowDefinition(columnCount: Int) : RowDefinition {
    val divider = buildString {
        append("+")
        repeat(columnCount) { append("-+") }
    }

    val body = buildString {
        append("|")
        repeat(columnCount) { append(".|") }
    }

    return RowDefinition(divider, body)
}

private fun printRemainingRows(count: Int, rowDefinition: RowDefinition) {
    repeat((count - 1).coerceAtLeast(0)) {
        println(rowDefinition.body)
        println(rowDefinition.divider)
    }
}

private fun printFirstRow(count: Int, rowDefinition: RowDefinition) {
    if (count > 0) {
        println(rowDefinition.divider.replaceRange(0..1, ".."))
        println(rowDefinition.body.replaceRange(0..1, ".."))
        println(rowDefinition.divider)
    }
}
