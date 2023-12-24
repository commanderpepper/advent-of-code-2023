package days.dayEight

import helpers.lcm
import helpers.readInput

private val nodeMap : MutableMap<String, Pair<String, String>> = mutableMapOf()

fun main(){
    val input = readInput("Day08")
    populateNodeMap(input)
    println(nodeMap)
    val steps = findStepsToDestination(input.first(), "AAA","ZZZ")
    println(steps)

    val startingNodes = findAllNodesEndingWithA()
    println(startingNodes)

    val aSteps = startingNodes.map {
        findStepsToDestination(instructions = input.first(), startingKey = it, targetChar = 'Z').toLong()
    }
    println(aSteps)
    println(aSteps.reduce{ prev, next -> prev.lcm(next) })
}

fun findAllNodesEndingWithA(): List<String>{
    return nodeMap.keys.filter { it[2] == 'A' }.toList()
}


fun findStepsToDestination(instructions: String, startingKey: String, target: String): Int {
    if(nodeMap.keys.contains(target).not()) return 0
    var steps = 0
    var key = startingKey

    while (true){
        instructions.forEach { instruction ->
            steps++
            if(instruction == 'L'){
                val destination = nodeMap[key]?.first
                if(destination == target){
                    return steps
                }
                else {
                    key = destination!!
                }
            }
            else if(instruction == 'R'){
                val destination = nodeMap[key]?.second
                if(destination == target){
                    return steps
                }
                else {
                    key = destination!!
                }
            }
        }
    }

    return steps
}

fun findStepsToDestination(instructions: String, startingKey: String, targetChar: Char): Int {
    if(nodeMap.keys.any{ it.last() == targetChar }.not()) return 0
    var steps = 0
    var key = startingKey

    while (true){
        instructions.forEach { instruction ->
            steps++
            if(instruction == 'L'){
                val destination = nodeMap[key]?.first
                if(destination!!.last() == targetChar){
                    return steps
                }
                else {
                    key = destination!!
                }
            }
            else if(instruction == 'R'){
                val destination = nodeMap[key]?.second
                if(destination!!.last() == targetChar){
                    return steps
                }
                else {
                    key = destination!!
                }
            }
        }
    }

    return steps
}

fun populateNodeMap(input: List<String>){
    for(i in 2 until input.size){
        val line = input[i]
        val keyAndNodes = line.split("=")
        val key = keyAndNodes.first().trim()
        val nodes = keyAndNodes.last().split(",")
        val nodeOne = nodes.first().filter { it.isLetter() || it.isDigit() }
        val nodeTwo = nodes.last().filter { it.isLetter() || it.isDigit() }
        nodeMap[key] = nodeOne to nodeTwo
    }
}