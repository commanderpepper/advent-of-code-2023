package days.dayThree

import helpers.readInput

fun main(){
    val dayOneInput = readInput("Day03")
    val dayTwoInput = readInput("Day03")
    println(dayOneInput)
    println(dayTwoInput)
    println(findPartNumbers(dayOneInput).sum())
}

fun findPartNumbers(schematic: List<String>): List<Int>{
    val partNumbers = mutableListOf<Int>()

    schematic.forEachIndexed { row, line ->
        var i = 0

        while(i < line.length){
            val possibleStartingNumber = line[i]
            if(possibleStartingNumber.isDigit()){
                var j = i + 1
                check@ while(j < line.length){
                    val possibleEndingNumber = line[j]
                    if(possibleEndingNumber.isDigit().not()){
                        val number = line.substring(i, j)
                        println(number)
                        if(isNumberValid(schematic = schematic, row = row, startingIndex = i, endingIndex = j)){
                            partNumbers.add(number.toInt())
                        }
                        i = j
                        break@check
                    }
                    if(j == line.length - 1){
                        if(possibleEndingNumber.isDigit()){
                            val number = line.substring(i)
                            println(number)
                            if(isNumberValid(schematic = schematic, row = row, startingIndex = i, endingIndex = j)){
                                partNumbers.add(number.toInt())
                            }
                            i = j
                            break@check
                        }
                    }
                    j++
                }
                i++
            }
            else {
                i++
            }
        }
    }
    return partNumbers
}

fun getRowAbove(schematic: List<String>, row: Int, startingIndex: Int, endingIndex: Int): String? {
    return if(row == 0) {
        null
    }
    else {
        schematic[row - 1].substring(startingIndex, endingIndex)
    }
}

fun getRowBelow(schematic: List<String>, row: Int, startingIndex: Int, endingIndex: Int): String? {
    return if(row >= schematic.size - 1) {
        null
    }
    else {
        schematic[row + 1].substring(startingIndex, endingIndex)
    }
}

fun getColumnBefore(schematic: List<String>, row: Int, startingIndex: Int): String? {
    if(startingIndex == 0){
        return null
    }
    if(row == 0){
        val left = schematic[row][startingIndex - 1]
        val bottomLeft = schematic[row + 1][startingIndex - 1]
        return "$left$bottomLeft"
    }
    else if(row >= schematic.size - 1){
        val left = schematic[row][startingIndex - 1]
        val topLeft = schematic[row - 1][startingIndex - 1]
        return "$topLeft$left"
    }
    else {
        val left = schematic[row][startingIndex - 1]
        val bottomLeft = schematic[row + 1][startingIndex - 1]
        val topLeft = schematic[row - 1][startingIndex - 1]
        return "$topLeft$left$bottomLeft"
    }
}

fun getColumnAfter(schematic: List<String>, row: Int, endingIndex: Int): String? {
    if(endingIndex >= schematic.first().length - 1){
        return null
    }
    if(row == 0){
        val right = schematic[row][endingIndex + 1]
        val bottomRight = schematic[row + 1][endingIndex + 1]
        return "$right$bottomRight"
    }
    else if(row >= schematic.size - 1){
        val right = schematic[row][endingIndex + 1]
        val topRight = schematic[row - 1][endingIndex + 1]
        return "$topRight$right"
    }
    else {
        val right = schematic[row][endingIndex + 1]
        val topRight = schematic[row - 1][endingIndex + 1]
        val bottomRight = schematic[row + 1][endingIndex + 1]
        return "$topRight$right$bottomRight"
    }
}

fun isNumberValid(schematic: List<String>, row: Int, startingIndex: Int, endingIndex: Int): Boolean {
    val rowAbove = getRowAbove(schematic, row = row, startingIndex = startingIndex, endingIndex = endingIndex)
    val rowBelow = getRowBelow(schematic, row = row, startingIndex = startingIndex, endingIndex = endingIndex)
    val columnBefore = getColumnBefore(schematic, row = row, startingIndex = startingIndex)
    val columnAfter = getColumnAfter(schematic, row = row, endingIndex = endingIndex - 1)

    return rowAbove.containSymbol() || rowBelow.containSymbol() || columnBefore.containSymbol() || columnAfter.containSymbol()
}

fun String?.containSymbol(): Boolean {
    return this?.any { it != '.' } ?: false
}