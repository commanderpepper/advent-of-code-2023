package days.daySeven

import helpers.readInput

fun main() {
    val dayOneInput = readInput("Day07")
    val dayTwoInput = readInput("Day07")
    println(dayOneInput)
    println(dayTwoInput)

    val allHands = getAllHands(dayOneInput)
    val sortedHands = allHands.sortedWith(comparator = HandComparator)

    sortedHands.forEach(::println)

    var winnings = 0
    sortedHands.forEachIndexed { index, hand ->
        winnings += (index + 1) * hand.bidAmount
    }
    println(winnings)
}

object HandComparator : Comparator<Hand> {
    override fun compare(handOne: Hand, handTwo: Hand): Int {
        val handOneStrength = handOne.handType.strength
        val handTwoStrength = handTwo.handType.strength

        return when {
            handOneStrength < handTwoStrength -> -1
            handOneStrength > handTwoStrength -> 1
            else -> determineTie(handOne = handOne, handTwo = handTwo)
        }
    }

    private fun determineTie(handOne: Hand, handTwo: Hand): Int {
        handOne.cards.zip(handTwo.cards){ a: Card, b: Card -> a to b }.forEach { (cardOne, cardTwo) ->
            if(cardOne.strength > cardTwo.strength){
                return 1
            }
            else if(cardOne.strength < cardTwo.strength){
                return -1
            }
        }

        return 0
    }

}

fun getAllHands(input: List<String>): List<Hand>{
    return input.map { hand ->
        val cards = hand.substring(startIndex = 0, endIndex = 5).map { it.toCard() }
        val bid = hand.substring(startIndex = 6).toInt()
        Hand(cards, bid)
    }
}

fun Char.toCard(): Card {
    return when(this){
        'A' -> Card.A
        'K' -> Card.K
        'Q' -> Card.Q
        'J' -> Card.J
        'T' -> Card.Ten
        '9' -> Card.Nine
        '8' -> Card.Eight
        '7' -> Card.Seven
        '6' -> Card.Six
        '5' -> Card.Five
        '4' -> Card.Four
        '3' -> Card.Three
        '2' -> Card.Two
        else -> {
            throw Exception("Can't determine card")
        }
    }
}

data class Hand(val cards: List<Card>, val bidAmount: Int) {

    val handType: HandType = determineHandType()
    private fun determineHandType(): HandType {
        val cardCount = mutableMapOf<Card, Int>()

        cards.forEach {
            cardCount.putIfAbsent(it, 0)
            cardCount[it] = cardCount[it]!! + 1
        }

        println(cardCount)

        if(cardCount.size == 1) return HandType.FIVE_OF_A_KIND
        if(cardCount.size == 2){
            val highestCount = cardCount.maxOf { it.value }
            return if(highestCount == 4) HandType.FOUR_OF_A_KIND else HandType.THREE_OF_A_KIND
        }
        if(cardCount.size == 3){
            val highestCount = cardCount.maxOf { it.value }
            return if(highestCount == 3) HandType.THREE_OF_A_KIND else HandType.TWO_PAIR
        }
        if(cardCount.size == 4){
            return HandType.PAIR
        }

        return HandType.HIGH_CARD
    }
}

enum class HandType(val strength: Int){
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    PAIR(2),
    HIGH_CARD(1)
}

enum class Card(val strength: Int){
    A(14),
    K(13),
    Q(12),
    J(11),
    Ten(10),
    Nine(9),
    Eight(8),
    Seven(7),
    Six(6),
    Five(5),
    Four(4),
    Three(3),
    Two(2)
}