package days.dayFour

import helpers.readInput

fun main(){
    val dayOneInput = readInput("Day04PartOne")
    val dayTwoInput = readInput("Day04PartTwo")
    println(dayOneInput)
    println(dayTwoInput)

    val scratchCards = getScratchCards(dayOneInput)
    println(scratchCards)
    val totalPoints = scratchCards.sumOf { it.findPoints() }
    println(totalPoints)

    val cardToCopies = getWinningCardAmount(dayTwoInput)
    println(cardToCopies.values.sum())
}

fun getWinningCardAmount(cards: List<String>): Map<Int, Int>{
    val scratchCards = getScratchCards(cards)

    val cardToAmount = mutableMapOf<Int, Int>()
    scratchCards.forEach {
        cardToAmount.put(it.number, 1)
    }

    scratchCards.forEach { scratchCard ->
        findCopiesForEachCard(cardToAmount, scratchCards, findWinningCopies(scratchCard, scratchCards))
    }

    return cardToAmount
}

fun findWinningCopies(scratchCard: ScratchCard, scratchCards: List<ScratchCard>): List<ScratchCard>{
    val copies = mutableListOf<ScratchCard>()

    if(scratchCard.isWinning()){
        val winningCardAmount = scratchCard.winningNumberAmount()
        for(i in scratchCard.number until  scratchCard.number + winningCardAmount){
            if(i < scratchCards.size){
                copies.add(scratchCards[i])
            }
        }
    }
    return copies
}

fun findCopiesForEachCard(cardToAmount: MutableMap<Int, Int>, scratchCards: List<ScratchCard>, winningCopies: List<ScratchCard>){
    if(winningCopies.isEmpty()){
        return
    }
    else {
        winningCopies.forEach { winningCard ->
            cardToAmount[winningCard.number] = cardToAmount[winningCard.number]!! + 1
            val recursiveCopies = findWinningCopies(winningCard, scratchCards)
            findCopiesForEachCard(cardToAmount, scratchCards, recursiveCopies)
        }
    }

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

    fun isWinning(): Boolean {
        return cardNumbers.any{ winningNumbers.contains(it) }
    }

    fun winningNumberAmount(): Int {
        return cardNumbers.count { winningNumbers.contains(it) }
    }
}