package days.daySeven

import helpers.readInput

fun main() {
    val dayOneInput = readInput("Day07PartOne")
    val dayTwoInput = readInput("Day07PartTwo")
    println(dayOneInput)
    println(dayTwoInput)

    println(solvePart1(dayOneInput))
    println(solvePart2(dayOneInput))
}

fun solvePart1(input: List<String>): Int =
    playCamelCards(input = input)

fun solvePart2(input: List<String>): Int =
    playCamelCards(input = input, true)

private fun playCamelCards(input: List<String>, jokersWild: Boolean = false): Int =
    input
        .asSequence()
        .map { row -> row.split(" ") }
        .map { parts -> HandTodd(parts.first(), parts.last().toInt(), jokersWild) }
        .sorted()
        .mapIndexed { index, hand -> hand.bid * (index + 1) }
        .sum()

private class HandTodd(cards: String, val bid: Int, jokersWild: Boolean) : Comparable<HandTodd> {
    private val identity: Int = calculateIdentity(
        cards,
        if (jokersWild) STRENGTH_WITH_JOKERS else STRENGTH_WITHOUT_JOKERS,
        if (jokersWild) this::calculateCategoryWithJokers else this::calculateCategoryWithoutJokers
    )

    private fun calculateIdentity(
        cards: String,
        strength: String,
        categoryCalculation: (String) -> List<Int>
    ): Int {
        val category = categoryCalculation(cards)
        return cards.fold(CATEGORIES.indexOf(category)) { acc, card ->
            (acc shl 4) or strength.indexOf(card)
        }
    }

    private fun calculateCategoryWithoutJokers(cards: String): List<Int> =
        cards.groupingBy { it }.eachCount().values.sortedDescending()

    private fun calculateCategoryWithJokers(cards: String): List<Int> {
        val cardsWithoutJokers = cards.filterNot { it == 'J' }
        val numberOfJokers = cards.length - cardsWithoutJokers.length

        return if (numberOfJokers == 5) listOf(5)
        else calculateCategoryWithoutJokers(cardsWithoutJokers).toMutableList().apply {
            this[0] += numberOfJokers
        }
    }

    override fun compareTo(other: HandTodd): Int =
        this.identity - other.identity

    companion object {
        private val CATEGORIES = listOf(
            listOf(1, 1, 1, 1, 1),
            listOf(2, 1, 1, 1),
            listOf(2, 2, 1),
            listOf(3, 1, 1),
            listOf(3, 2),
            listOf(4, 1),
            listOf(5)
        )

        private const val STRENGTH_WITHOUT_JOKERS = "23456789TJQKA"
        private const val STRENGTH_WITH_JOKERS = "J23456789TQKA"
    }
}