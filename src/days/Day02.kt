package days

import println
import readInput

fun main(){
    val dayOneInput = readInput("Day02PartOne")
    val dayTwoInput = readInput("Day02PartTwo")
    println(dayOneInput)
    println(dayTwoInput)

    println(sumOfValidGameIdsPartOne(dayOneInput))
}

private fun sumOfValidGameIdsPartOne(dayOneInput: List<String>): Int {
    val dayOneGameMap = mutableMapOf<Int, CubeGame>()

    val splitGames = dayOneInput.map { it.split(":") }
    splitGames.forEach {
        val key = it.first()
        val sets = it.last().split(";")

        val numberedKey = key.split(" ").last().toInt()
        dayOneGameMap[numberedKey] = CubeGame()

        sets.forEach { set ->
            val cubes = set.split(",")
            cubes.forEach { cube ->
                val amountAndColor = cube.trim().split(" ")
                val amount = amountAndColor.first().toInt()
                val color = amountAndColor.last()
                val cubeGame = dayOneGameMap[numberedKey]
                when (color) {
                    "blue" -> {
                        if (amount > cubeGame!!.blueCubes) {
                            dayOneGameMap[numberedKey] = cubeGame!!.copy(blueCubes = amount)
                        }
                    }

                    "red" -> {
                        if (amount > cubeGame!!.redCubes) {
                            dayOneGameMap[numberedKey] = cubeGame!!.copy(redCubes = amount)
                        }
                    }

                    "green" -> {
                        if (amount > cubeGame!!.greenCubes) {
                            dayOneGameMap[numberedKey] = cubeGame!!.copy(greenCubes = amount)
                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }
    var dayOneAmount = 0
    dayOneGameMap.forEach { (id, game) ->
        if (game.isGamePossible(12, 14, 13)) dayOneAmount += id
    }
    return dayOneAmount
}

data class CubeGame(val redCubes: Int = 0, val blueCubes: Int = 0, val greenCubes: Int = 0)

fun CubeGame.isGamePossible(possibleRedCubes: Int, possibleBlueCubes: Int, possibleGreenCubes: Int): Boolean {
    return this.redCubes <= possibleRedCubes && this.blueCubes <= possibleBlueCubes && this.greenCubes <= possibleGreenCubes
}