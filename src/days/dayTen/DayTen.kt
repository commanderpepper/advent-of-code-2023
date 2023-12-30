package days.dayTen

import helpers.readInput

fun main(){
    val input = readInput("Day10")

    val rowSIndex = input.indexOfFirst { line -> line.contains("S") }
    val colSIndex = input[rowSIndex].indexOfFirst { pipe -> pipe == 'S' }

    val leftBound = 0
    val rightBound = input.first().length
    val topBound = 0
    val bottomBound = input.size

    val distanceFromStart = List(size = input.size){ index ->
        MutableList(input.first().length){ -1 }
    }

    distanceFromStart.forEach(::println)

    input.forEachIndexed { rowIndex, pipes ->
        pipes.forEachIndexed { colIndex, pipe ->

        }
    }

    for(i in 0 until input.size){
        for(j in 0 until input.first().length){
            if(j != leftBound){

            }
            println(input[i][j])
        }
    }
}

fun findFarthestPoint(pipes: List<String>, startingPoint: Pair<Int, Int>): Int {
    val pipeStack = mutableListOf<Pair<Int, Int>>()
    val pipeDistances = mutableMapOf<Pair<Int, Int>, Int>()

    pipeDistances[startingPoint] = 0

    return pipeDistances.values.max()
}

/**
 * | is a vertical pipe connecting north and south. L, J, 7, F
 * - is a horizontal pipe connecting east and west. L, J, 7, F
 * L is a 90-degree bend connecting north and east. |, -, J, 7
 * J is a 90-degree bend connecting north and west. |, -, L, F
 * 7 is a 90-degree bend connecting south and west. |, -, J, F
 * F is a 90-degree bend connecting south and east. |, -, J, F
 * . is ground; there is no pipe in this tile.
 * S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
 */
fun Char.isTopConnecting(pipeAbove: Char): Boolean {
    return when(this){
        '|' -> pipeAbove == '|' || pipeAbove == '7' || pipeAbove == 'F'
        'J' -> pipeAbove == '|' || pipeAbove == '7' || pipeAbove == 'F'
        'L' -> pipeAbove == '|' || pipeAbove == '7' || pipeAbove == 'F'
        else -> false
    }
}

fun Char.isBottomConnecting(pipeBelow: Char): Boolean {
    return when(this){
        '|' -> pipeBelow == '|' || pipeBelow == 'J' || pipeBelow == 'L'
        '7' -> pipeBelow == '|' || pipeBelow == 'J' || pipeBelow == 'L'
        'F' -> pipeBelow == '|' || pipeBelow == 'J' || pipeBelow == 'L'
        else -> false
    }
}

fun Char.isLeftConnecting(leftPipe: Char): Boolean {
    return when(this){
        '-' -> leftPipe == '-' || leftPipe == 'L' || leftPipe == 'F'
        'J' -> leftPipe == '-' || leftPipe == 'L' || leftPipe == 'F'
        '7' -> leftPipe == '-' || leftPipe == 'L' || leftPipe == 'F'
        else -> false
    }
}

fun Char.isRightConnecting(rightPipe: Char): Boolean {
    return when(this){
        '-' -> rightPipe == '-' || rightPipe == 'J' || rightPipe == '7'
        'L' -> rightPipe == '-' || rightPipe == 'J' || rightPipe == '7'
        'F' -> rightPipe == '-' || rightPipe == 'J' || rightPipe == '7'
        else -> false
    }
}