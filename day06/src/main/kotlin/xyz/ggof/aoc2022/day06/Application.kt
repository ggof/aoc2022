package xyz.ggof.aoc2022.day06

import java.io.File

fun String.isMarker() = groupBy { it }.values.all { it.size == 1 }

fun String.endOfMarker(size: Int) = size + windowed(size).indexOfFirst { it.isMarker() }

fun String.part1() = endOfMarker(4)
fun String.part2() = endOfMarker(14)

fun main() {
	val input = File("input.txt").readText()

	println("part 1: ${input.part1()}")
	println("part 2: ${input.part2()}")
}