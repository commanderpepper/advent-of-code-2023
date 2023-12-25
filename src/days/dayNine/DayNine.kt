package days.dayNine

import helpers.readInput

fun main(){
    val input = readInput("Day09")
    val histories = getHistories(input)

    val total = histories.map {
        extrapolateNextValue(it)
    }.sum()
    println(total)

    val prevTotal = histories.map {
        extrapolatePreviousValue(it)
    }.sum()
    println(prevTotal)
}

fun getHistories(input: List<String>): List<List<Long>> {
    return input.map { history ->
        history.split(" ").map { it.toLong() }
    }
}

fun extrapolateNextValue(history: List<Long>): Long {
    val derivedValues = mutableListOf<MutableList<Long>>()

    var listToBeExamined = history
    while (listToBeExamined.all { it == 0L }.not()){
        derivedValues.add(0, listToBeExamined.toMutableList())
        listToBeExamined = listToBeExamined.zipWithNext { a, b -> b - a }
    }

    for(i in 0 until derivedValues.size - 1){
        val currentHistory = derivedValues[i]
        val historyOneStepAbove = derivedValues[i + 1]
        historyOneStepAbove.add(currentHistory.last() + historyOneStepAbove.last())
    }

    return derivedValues.last().last()
}

fun extrapolatePreviousValue(history: List<Long>): Long {
    val derivedValues = mutableListOf<MutableList<Long>>()

    var listToBeExamined = history
    while (listToBeExamined.all { it == 0L }.not()){
        derivedValues.add(0, listToBeExamined.toMutableList())
        listToBeExamined = listToBeExamined.zipWithNext { a, b -> b - a }
    }

    for(i in 0 until derivedValues.size - 1){
        val currentHistory = derivedValues[i]
        val historyOneStepAbove = derivedValues[i + 1]
        historyOneStepAbove.add(0, historyOneStepAbove.first() - currentHistory.first())
    }

    return derivedValues.last().first()
}