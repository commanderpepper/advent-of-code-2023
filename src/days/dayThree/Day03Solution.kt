package days.dayThree

import helpers.Point2D
import helpers.readInput

fun main(){
    val dayOneInput = readInput("Day03")
    val dayTwoInput = readInput("Day03")
    println(dayOneInput)
    println(dayTwoInput)

    val (dayOneNumbers, dayOneSymbols) = parseInput(dayOneInput)
    val dayOneSum = dayOneNumbers
        .filter { number -> number.isAdjacentToAny(dayOneSymbols) }
        .sumOf { it.toInt() }

    println(dayOneSum)

    val (dayTwoNumbers, dayTwoSymbols) = parseInput(dayTwoInput){ char ->
        char == '*'
    }
    println(dayTwoNumbers)
    println(dayTwoSymbols)

    val dayTwoLocations = dayTwoNumbers.filter { numberLocation -> numberLocation.isAdjacentToAny(dayTwoSymbols)  }
        .map { numberLocation -> numberLocation to dayTwoNumbers.filter { it  != numberLocation && it.isAdjacentToAny(dayTwoSymbols) }
    }

    val dayTwoSum = dayTwoSymbols.map { gearSymbolLocation ->
        dayTwoLocations.map { (numberLocation, allOtherLocations) ->
            val otherNumber = allOtherLocations.firstOrNull { it.locations.contains(gearSymbolLocation) }
            if(numberLocation.locations.contains(gearSymbolLocation) && otherNumber != null){
                numberLocation.toInt() * otherNumber.toInt()
            }
            else {
                0
            }
        }
    }.flatten().toSet().sum()

    println(dayTwoSum)
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

    override fun toString(): String {
        return this.toInt().toString()
    }
}