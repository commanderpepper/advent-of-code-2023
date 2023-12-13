package days.dayOne

import helpers.println
import helpers.readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }


    val dayOneInput = readInput("Day01PartOne")
    val dayTwoInput = readInput("Day01PartTwo")
    part1(dayOneInput).println()
    part2(dayTwoInput).println()

    val dayOneValues = dayOneInput.map(::getCalibrationValuePartOne)
    println(dayOneValues)
    println(dayOneValues.sum())

    val dayTwoValues = dayTwoInput.map(::calibrateCalibrationLine)
    println(dayTwoValues)
    println(dayTwoValues.sum())
}

fun getCalibrationValuePartOne(calibrationLine: String): Int {
    val first = getFirstDigitPartOne(calibrationLine)
    val last = getLastDigitPartOne(calibrationLine)
    return "$first$last".toInt()
}

fun getFirstDigitPartOne(calibrationLine: String): String {
    var digit = ""

    checkForDigit@ for(i in 0 until calibrationLine.length) {
        val character = calibrationLine[i]
        if(character.isDigit()){
            digit = character.toString()
            break@checkForDigit
        }
    }

    return digit
}

fun getLastDigitPartOne(calibrationLine: String): String {
    var digit = ""

    checkForDigit@ for (i in calibrationLine.length - 1 downTo 0){
        val character = calibrationLine.get(i)
        if(character.isDigit()){
            digit = character.toString()
            break@checkForDigit
        }
    }

    return digit
}

fun getCalibrationValuePartTwo(calibrationLine: String): Int {
    val first = getFirstDigitPartTwo(calibrationLine)
    val last = getLastDigitPartTwo(calibrationLine)
    return "$first$last".toInt()
}

fun getFirstDigitPartTwo(calibrationLine: String): Int {
    var digit: Int = 0
    checkForDigit@ for(i in 0 until calibrationLine.length ){
        val character = calibrationLine[i]
        if(character.isDigit()){
            digit = character.digitToInt()
            break@checkForDigit
        }
        else {
            for(j in 3 .. 5){
                val startingIndex = i
                val endingIndex = i + j
                if(endingIndex < calibrationLine.length){
                    val possibleDigit = getDigit(calibrationLine.substring(startingIndex, endingIndex))
                    if(possibleDigit != null){
                        digit = possibleDigit
                        break@checkForDigit
                    }
                }
            }
        }
    }
    return digit
}

fun getLastDigitPartTwo(calibrationLine: String): Int {
    var digit: Int = 0
    checkForDigit@ for(i in calibrationLine.length - 1 downTo 0 ){
        val character = calibrationLine[i]
        if(character.isDigit()){
            digit = character.digitToInt()
            break@checkForDigit
        }
        else {
            val threeLengthWord = calibrationLine.substring(floorValue(value = i - 2), i + 1)
            val fourLengthWord = calibrationLine.substring(floorValue(value = i - 3), i + 1)
            val fiveLengthWord = calibrationLine.substring(floorValue(value = i - 4), i + 1)
            val threeWordDigit = getDigit(threeLengthWord)
            val fourWordDigit = getDigit(fourLengthWord)
            val fiveWordDigit = getDigit(fiveLengthWord)
            when {
                threeWordDigit != null -> {
                    digit = threeWordDigit
                    break@checkForDigit
                }
                fourWordDigit != null -> {
                    digit = fourWordDigit
                    break@checkForDigit
                }
                fiveWordDigit != null -> {
                    digit = fiveWordDigit
                    break@checkForDigit
                }
            }
        }
    }
    return digit
}

fun calibrateCalibrationLine(calibrationLine: String): Int {
    val first = getFirstDigitPartTwo(calibrationLine)
    val second = getLastDigitPartTwo(calibrationLine)
    return "$first$second".toInt()
}

fun floorValue(floorValue: Int = 0 , value: Int): Int {
    if(value < floorValue) return floorValue else return value
}

fun getDigit(digit : String): Int? {
    return when(digit){
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> {
            null
        }
    }
}