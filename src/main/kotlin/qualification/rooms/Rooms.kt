package qualification.rooms

data class Corridor(val a: Room, val b: Room)
data class Room(
    val id: Long,
    var corridorCount: Int? = null,
    val corridors: MutableList<Corridor> = mutableListOf(),
    var visited: Boolean = false
)

//fun main(args: Array<String>) {
//    val caseCount = readLine()?.toInt() ?: error("Case count not found!")
//    val definitionRegex = """([0-9]+) ([0-9]+)""".toRegex()
//
//    repeat(caseCount) { caseIndex ->
//        val definitionLine = readLine() ?: error("Definition for case $caseIndex not found!")
//        val (roomCount, operations) = definitionRegex.find(definitionLine)
//            ?.destructured
//            ?: error("Failed to parse definition for case $caseIndex")
//
//
//        var minCorridors: Long = roomCount.toLong() - 1
//
//        var maxKnown: Long = 0
//        var maxSize: Long = roomCount.toLong()
//
//        val rooms = (1..roomCount.toLong()).map { Room(it) }
//        var haveWalked = false
//        val unvisitedRooms = (1..roomCount.toLong()).toMutableList()
//
//        repeat(operations.toInt() + 1) { operation ->
//            val resultLine = readLine() ?: error("result for case $caseIndex not found!")
//            val (roomId, corridorCount) = definitionRegex.find(resultLine)
//                ?.destructured
//                ?: error("Failed to parse result line for case $caseIndex at operation $operation")
//
//            val currentRoom = rooms[roomId.toInt() - 1]
//            currentRoom.corridorCount = corridorCount.toInt()
//            if(!currentRoom.visited) {
//                currentRoom.visited = true
//                maxKnown += corridorCount.toInt()
//                maxSize -= 1
//            }
//
//            unvisitedRooms.remove(roomId.toLong())
//
//            if(operation == operations.toInt()) {
//                val currentMax = ((maxSize * (maxSize - 1L)) / 2L)
//                val max = maxKnown + currentMax
//                val guess = (minCorridors + max) / 2
//                print("E $max")
//            } else {
//                when {
//                    haveWalked -> {
//                        haveWalked = false
//                        println("T ${ unvisitedRooms.random() }")
//                    }
//                    else -> {
//                        haveWalked = true
//                        println("W")
//                    }
//                }
//            }
//        }
//    }
//}

fun main(args: Array<String>) {
    val caseCount = readLine()?.toInt() ?: error("Case count not found!")
    val definitionRegex = """([0-9]+) ([0-9]+)""".toRegex()

    repeat(caseCount) { caseIndex ->
        val definitionLine = readLine() ?: error("Definition for case $caseIndex not found!")
        val (roomCount, operations) = definitionRegex.find(definitionLine)
            ?.destructured
            ?: error("Failed to parse definition for case $caseIndex")


        var minCorridors: Long = roomCount.toLong() - 1
        var maxCorridors: Long = (roomCount.toLong() * (roomCount.toLong() - 1L)) / 2L

        var maxKnown: Long = 0
        var knownCorridors: Long = 0

        val rooms = (1..roomCount.toLong()).map { Room(it) }
        var previousRoom: Room? = null
        var haveWalked = false
        val unvisitedRooms = (1..roomCount.toLong()).toMutableList()

        repeat(operations.toInt() + 1) { operation ->
            val resultLine = readLine() ?: error("result for case $caseIndex not found!")
            val (roomId, corridorCount) = definitionRegex.find(resultLine)
                ?.destructured
                ?: error("Failed to parse result line for case $caseIndex at operation $operation")

            val currentRoom = rooms[roomId.toInt() - 1]
            unvisitedRooms.remove(roomId.toLong())

            previousRoom?.let {
                if(haveWalked && currentRoom.corridors.none {
                        (it.a == previousRoom && it.b == currentRoom) || (it.b == previousRoom && it.a == currentRoom)
                    }
                ) {
                    val newCorridor = Corridor(it, currentRoom)
                    it.corridors.add(newCorridor)
                    currentRoom.corridors.add(newCorridor)
                    maxCorridors -= 1
                    knownCorridors += 1
                    maxKnown -= 1
                }
            }

            if(currentRoom.corridorCount == null) {
                currentRoom.corridorCount = corridorCount.toInt()
                maxKnown += corridorCount.toInt()
                maxCorridors -= (minCorridors - corridorCount.toInt())
            }

            previousRoom = currentRoom

            if(operation == operations.toInt()) {
                val currentMax = ((unvisitedRooms.size * (unvisitedRooms.size - 1L)) / 2L)
                val max = (maxKnown - knownCorridors) + currentMax
                val guess = ((maxKnown - knownCorridors) + max) / 2
                print("E $max")
            } else {
                when {
                    haveWalked -> {
                        haveWalked = false
                        println("T ${ unvisitedRooms.random() }")
                    }
                    !haveWalked && corridorCount.toInt() != 1 -> {
                        haveWalked = true
                        println("W")
                    }
                }
            }
        }
    }
}

//fun main(args: Array<String>) {
//    val caseCount = readLine()?.toInt() ?: error("Case count not found!")
//    val definitionRegex = """([0-9]+) ([0-9]+)""".toRegex()
//
//    repeat(caseCount) { caseIndex ->
//        val definitionLine = readLine() ?: error("Definition for case $caseIndex not found!")
//        val (roomCount, operations) = definitionRegex.find(definitionLine)
//            ?.destructured
//            ?: error("Failed to parse definition for case $caseIndex")
//
//
//        val minCorridors: Long = roomCount.toLong()
//        val maxCorridors: Long = roomCount.toLong() * (roomCount.toInt() - 1L) / 2L
//        val rooms = (1..roomCount.toLong()).map { Room(it) }
//        var previousRoom: Room? = null
//        var haveWalked = false
//        var guess: Long = minCorridors
//        val unvisitedRooms = (1..roomCount.toInt()).toMutableList()
//
//        repeat(operations.toInt() + 1) { operation ->
//            val resultLine = readLine() ?: error("result for case $caseIndex not found!")
//            val (roomId, corridorCount) = definitionRegex.find(resultLine)
//                ?.destructured
//                ?: error("Failed to parse result line for case $caseIndex at operation $operation")
//
//            val currentRoom = rooms[roomId.toInt() - 1]
//            unvisitedRooms.remove(roomId.toInt())
//
//            previousRoom?.let {
//                if(haveWalked && currentRoom.corridors.none {
//                        (it.a == previousRoom && it.b == currentRoom) || (it.b == previousRoom && it.a == currentRoom)
//                    }
//                ) {
//                    val newCorridor = Corridor(it, currentRoom)
//                    it.corridors.add(newCorridor)
//                    currentRoom.corridors.add(newCorridor)
//                    haveWalked = false
//                    guess -= 1
//                }
//            }
//
//            if(currentRoom.corridorCount == null) {
//                currentRoom.corridorCount = corridorCount.toInt()
//                guess += corridorCount.toInt()
//            }
//
//            previousRoom = currentRoom
//
//            if(operation == operations.toInt()) {
//                val middle = (minCorridors + guess) / 2
//                print("E $middle")
//            } else {
//                when {
//                    corridorCount.toInt() == 1 || currentRoom.corridors.size == currentRoom.corridorCount -> {
//                        println("T ${ unvisitedRooms.random() }")
//                    }
//                    else -> {
//                        haveWalked = true
//                        println("W")
//                    }
//                }
//            }
//        }
//    }
//}