package qualification.dmillion

fun main(args: Array<String>) {
    val caseCount = readLine()?.toInt() ?: error("Case count not found!")

    repeat(caseCount) { caseIndex ->
        print("Case #${ caseIndex + 1 }: ")

        val dieCount = readLine()?.toInt() ?: error("Die count not found!")
        val dice = readLine()
            ?.split(" ")
            ?.map { it.toInt() }
            ?.sortedBy { it }
            ?: error("Dice input not found!")

        val length = dice.checkDieAvailable()
        println(length)
    }
}

private fun List<Int>.checkDieAvailable() = fold(0) { running, next ->
    if(next >= running + 1) running + 1 else running
}