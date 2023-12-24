package days.daySix

import helpers.readInput

fun main(){
    val dayOneInput = readInput("Day06")
    val dayTwoInput = readInput("Day06")
    println(dayOneInput)
    println(dayTwoInput)

    val dayOneRaces = getToyBoatRaces(dayOneInput)
    println(dayOneRaces)
    val numberOfWaysToWin = dayOneRaces.map(::getNumberOfWaysToBeatBigRace)
    println(numberOfWaysToWin)
    println(numberOfWaysToWin.fold(1){ a,b -> a * b })

    val dayTwoRace = getToyBoatRace(dayTwoInput)
    println(dayTwoRace)
    println(getNumberOfWaysToBeatBigRace(dayTwoRace))
}

fun getNumberOfWaysToBeatRace(toyBoatRace: ToyBoatRace): Int {
    var numberOfWaysToWin = 0

    for(heldButtonLength in 1L until toyBoatRace.time){
        var distanceCovered = 0L
        var time = 0
        val timeLeft = toyBoatRace.time - heldButtonLength
        while(time < timeLeft){
            distanceCovered += heldButtonLength
            time++
        }
        if(distanceCovered > toyBoatRace.recordDistance){
            numberOfWaysToWin++
        }
    }

    return numberOfWaysToWin
}

fun getNumberOfWaysToBeatBigRace(toyBoatRace: ToyBoatRace): Int {
    var numberOfWaysToWin = 0

    for(heldButtonLength in 1L until toyBoatRace.time){
        val timeLeft = toyBoatRace.time - heldButtonLength
        val possibleDistance = timeLeft * heldButtonLength
        if(possibleDistance > toyBoatRace.recordDistance){
            numberOfWaysToWin++
        }
    }

    return numberOfWaysToWin
}

fun getToyBoatRaces(input: List<String>): List<ToyBoatRace>{
    val times = input[0].split(":").last().split(" ").map { it.trim() }.filter { it.isNotEmpty() }.map { it.toLong() }
    val distances = input[1].split(":").last().split(" ").map { it.trim() }.filter { it.isNotEmpty() }.map { it.toLong() }
    return times.zip(distances){ time, distance ->
        ToyBoatRace(time = time, recordDistance = distance)
    }
}

fun getToyBoatRace(input: List<String>): ToyBoatRace {
    val time = input[0].split(":").last().split(" ").map { it.trim() }.filter { it.isNotEmpty() }.joinToString("").toLong()
    val distance = input[1].split(":").last().split(" ").map { it.trim() }.filter { it.isNotEmpty() }.joinToString("").toLong()

    return ToyBoatRace(time = time, recordDistance = distance)
}

data class ToyBoatRace(val time: Long, val recordDistance: Long)