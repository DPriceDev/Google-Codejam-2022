package qualification.chainreactions

data class Module(
    val funFactor: Long,
    var target: Module? = null,
    val links: MutableList<Module> = mutableListOf(),
    var resolvedFunFactor: Long? = null
)

fun main(args: Array<String>) {
    val caseCount = readLine()?.toInt() ?: error("Case count not found!")

    repeat(caseCount) { caseIndex ->
        print("Case #${caseIndex + 1}: ")
        val moduleCount = readLine()?.toInt() ?: error("Module count for case $caseIndex not found!")

        val rawModules = parseModules(moduleCount)
        val linkedModules = linkModules(rawModules)

        val queue: MutableList<Module> = linkedModules
            .filter { it.links.isEmpty() }
            .toMutableList()

        queue.forEach { it.resolvedFunFactor = it.funFactor }

        var total = 0L

        while(queue.isNotEmpty()) {
            val module = queue.first()
            queue.removeAt(0)

            module.target?.let { targetModule ->
                if(targetModule.resolvedFunFactor == null) {

                    when (targetModule.links.size) {
                        1 -> {
                            targetModule.resolvedFunFactor =
                                maxOf(module.resolvedFunFactor ?: 0, targetModule.funFactor)
                            queue.add(targetModule)
                        }
                        else -> {
                            if (targetModule.links.all { it.resolvedFunFactor != null }) {
                                targetModule.resolvedFunFactor =
                                    targetModule.links.minOf { it.resolvedFunFactor ?: Long.MAX_VALUE }
                                targetModule.resolvedFunFactor =
                                    maxOf(targetModule.funFactor, targetModule.resolvedFunFactor ?: 0)
                                total += targetModule.links.sumOf {
                                    it.resolvedFunFactor ?: 0
                                } - targetModule.links.minOf { it.resolvedFunFactor ?: 0 }
                                queue.add(targetModule)
                            } else {

                            }
                        }
                    }
                }
            } ?: total.apply {
                total += module.resolvedFunFactor ?: 0
            }
        }

        println(total)
    }
}

fun parseModules(count: Int): List<Module> {
    val factors = readLine()?.split(" ")?.map { it.toLong() } ?: error("failed to parse module fun factors")

    return (0 until count).map { index ->
        Module(factors[index])
    }
}

fun linkModules(modules: List<Module>): List<Module> {
    val targets = readLine()?.split(" ")?.map { it.toInt() } ?: error("failed to parse module targets")

    modules.forEachIndexed { index, module ->
        if(targets[index] == 0) return@forEachIndexed

        module.target = modules[targets[index] - 1]
        modules[targets[index] - 1].links.add(module)
    }

    return modules
}
