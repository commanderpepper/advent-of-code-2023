package days.dayFive

import helpers.readInput

fun main(){
    val dayOneInput = readInput("Day05PartOne")
    val dayTwoInput = readInput("Day05PartTwo")
    println(dayOneInput)
    println(dayTwoInput)

    val seeds = getSeeds(dayOneInput.first())
    println(seeds)

    val seedToSoil = getAlmanacMap(dayOneInput, "seed-to-soil map:")
    val soilToFertilizer = getAlmanacMap(dayOneInput, "soil-to-fertilizer map:")
    val fertilizerToWater = getAlmanacMap(dayOneInput, "fertilizer-to-water map:")
    val waterToLight = getAlmanacMap(dayOneInput, "water-to-light map:")
    val lightToTemperature = getAlmanacMap(dayOneInput, "light-to-temperature map:")
    val temperatureToHumidity = getAlmanacMap(dayOneInput, "temperature-to-humidity map:")
    val humidityToLocation = getAlmanacMap(dayOneInput, "humidity-to-location map:")

    val locations = seeds.map { seed ->
        seedToSoil.findDestination(seed.seedType)
    }.map {
        soilToFertilizer.findDestination(it)
    }.map {
        fertilizerToWater.findDestination(it)
    }.map {
        waterToLight.findDestination(it)
    }.map {
        lightToTemperature.findDestination(it)
    }.map {
        temperatureToHumidity.findDestination(it)
    }.map {
        humidityToLocation.findDestination(it)
    }

    println(locations.min())
}

fun getSeeds(seedsAsString: String): List<Seed>{
    val seeds = seedsAsString.split(":")
        .last()
        .split(" ")
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .map { Seed(it.toLong()) }
    return seeds
}

fun getAlmanacMap(data: List<String>, target: String): AlmanacMap{
    val index = data.indexOfFirst { it.contains(target) }
    val map = mutableListOf<AlmanacCategory>()
    if(index != -1){
        var i = index + 1
        while(i < data.size && data[i].any { it.isDigit() }){
            val mapLine = data[i]
            val numbers = mapLine.split(" ")
            val source = numbers[1]
            val destination = numbers[0]
            val range = numbers[2]
            map.add(AlmanacCategory(source = source.toLong(), destination = destination.toLong(), range = range.toLong()))
            i++
        }
    }
    return AlmanacMap(map)
}

data class Seed(val seedType: Long)

data class AlmanacMap(val map: List<AlmanacCategory>) {
    fun findDestination(source: Long): Long {
        var destination = source
        map.forEach { category ->
            val lowerSource = category.source
            val upperSource = category.source + category.range
            if(source in lowerSource..upperSource){
                val delta = source - lowerSource
                destination = category.destination + delta
            }
        }
        return destination
    }
}

data class AlmanacCategory(val source: Long, val destination: Long, val range: Long)