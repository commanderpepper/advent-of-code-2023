package days.dayFour

import days.dayThree.findPartNumbers
import helpers.readInput
import kotlin.math.pow

fun main(){
    val dayOneInput = readInput("Day04PartOne")
    val dayTwoInput = readInput("Day04PartTwo")
    println(dayOneInput)
    println(dayTwoInput)

    val scratchCards = getScratchCards(dayOneInput)
    println(scratchCards)
    val totalPoints  =scratchCards.sumOf { it.findPoints() }
    println(totalPoints)
}

fun getScratchCards(cards: List<String>): List<ScratchCard>{
    return cards.map { card ->
        val cardNameAndNumbers = card.split(":")
        val cardName = cardNameAndNumbers.first().filter { it.isDigit() }
        val winningAndCardNumbers = cardNameAndNumbers.last().split("|")
        val winningNumbers = winningAndCardNumbers.first().trim().split(" ").filter { it.isNotEmpty() }
        val cardNumbers = winningAndCardNumbers.last().trim().split(" ").filter { it.isNotEmpty() }
        ScratchCard(
            number = cardName.toInt(),
            winningNumbers = winningNumbers.map(String::toInt),
            cardNumbers = cardNumbers.map(String::toInt)
        )
    }
}

data class ScratchCard(val number: Int, val winningNumbers: List<Int>, val cardNumbers: List<Int>) {
    fun findPoints(): Int {
        var count = 0
        var points = 0
        cardNumbers.forEach { number ->
            if(winningNumbers.contains(number)){
                count++
            }
        }
        repeat(count){
            if(points == 0) {
                points = 1
            }
            else {
                points *= 2
            }
        }
        return points
    }
}