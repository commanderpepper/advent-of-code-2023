package days.dayFive

import helpers.readInput

//Solution from https://todd.ginsberg.com/

fun main(){
    val dayOneInput = readInput("Day05")
    val dayTwoInput = readInput("Day05")
    println(dayOneInput)
    println(dayTwoInput)

    val seedsPart1: List<Long> = parsePart1Seeds(dayOneInput)
    val seedsPart2: Set<LongRange> = parsePart2Seeds(dayTwoInput)
    val ranges: List<Set<RangePair>> = parseRanges(dayOneInput)

    println(solvePart1(seedsPart1, ranges))
    println(solvePart2(seedsPart2, ranges))

}

private fun parsePart1Seeds(input: List<String>): List<Long> =
    input.first().substringAfter(":").trim().split(" ").map { it.toLong() }

private fun parsePart2Seeds(input: List<String>): Set<LongRange> =
    input.first().substringAfter(":").trim().split(" ")
        .map { it.toLong() }.chunked(2).map {
            it.first()..<it.first() + it.last()
        }.toSet()

private fun parseRanges(input: List<String>): List<Set<RangePair>> =
    input.drop(2).joinToString("\n").split("\n\n").map {
        it.split("\n").drop(1).map { line -> RangePair.of(line) }.toSet()
    }

fun solvePart1(seedsPartOne: List<Long>, ranges: List<Set<RangePair>>): Long =
    seedsPartOne.minOf { seed ->
        ranges.fold(seed) { acc, ranges ->
            ranges.firstOrNull { acc in it }?.translate(acc) ?: acc
        }
    }

fun solvePart2(seedsPartTwo: Set<LongRange>, ranges: List<Set<RangePair>>): Long {

    val rangesReversed = ranges.map { range -> range.map { it.flip() } }.reversed()

    return generateSequence(0L, Long::inc).first { location ->
        val seed = rangesReversed.fold(location) { acc, ranges ->
            ranges.firstOrNull { acc in it }?.translate(acc) ?: acc
        }
        seedsPartTwo.any { seedRange -> seed in seedRange }
    }
}

data class RangePair(
    private val source: LongRange,
    private val destination: LongRange
) {

    fun translate(num: Long): Long =
        destination.first + (num - source.first)

    fun flip(): RangePair =
        RangePair(destination, source)

    operator fun contains(num: Long): Boolean =
        num in source

    companion object {
        fun of(row: String): RangePair {
            val parts = row.split(" ").map { it.toLong() }
            return RangePair(
                parts[1]..<(parts[1] + parts[2]),
                parts[0]..<(parts[0] + parts[2])
            )
        }
    }
}