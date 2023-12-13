package days.dayThree

import helpers.Point2D
import helpers.readInput

fun main(){
    val dayOneInput = readInput("Day03PartOne")
    val dayTwoInput = readInput("Day03PartTwo")
    println(dayOneInput)
    println(dayTwoInput)

    val (dayOneNumbers, dayOneSymbols) = parseInput(dayOneInput)
    val dayOneSum = dayOneNumbers
        .filter { number -> number.isAdjacentToAny(dayOneSymbols) }
        .sumOf { it.toInt() }

    println(dayOneSum)
}

private fun parseInput(
    input: List<String>,
    takeSymbol: (Char) -> Boolean = { it != '.' }
): Pair<Set<NumberLocation>, Set<Point2D>> {

    val numbers = mutableSetOf<NumberLocation>()
    val symbols = mutableSetOf<Point2D>()
    var workingNumber = NumberLocation()

    input
        .forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c.isDigit()) {
                    workingNumber.add(c, Point2D(x, y))
                } else {
                    if (workingNumber.isNotEmpty()) {
                        numbers.add(workingNumber)
                        workingNumber = NumberLocation()
                    }
                    if(takeSymbol(c)) {
                        symbols.add(Point2D(x, y))
                    }
                }
            }
            // Check at end of row that we don't miss a number.
            if (workingNumber.isNotEmpty()) {
                numbers.add(workingNumber)
                workingNumber = NumberLocation()
            }
        }
    return numbers to symbols
}

private class NumberLocation {
    val number = mutableListOf<Char>()
    val locations = mutableSetOf<Point2D>()

    fun add(c: Char, location: Point2D) {
        number.add(c)
        locations.addAll(location.neighbors())
    }

    fun isNotEmpty() =
        number.isNotEmpty()

    fun isAdjacentToAny(points: Set<Point2D>): Boolean =
        locations.intersect(points).isNotEmpty()

    fun toInt(): Int =
        number.joinToString("").toInt()
}