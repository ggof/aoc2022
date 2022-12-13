package xyz.ggof.aoc2022.day10

import java.io.File

fun interface Instruction : (Int) -> Int

val noop = Instruction { it }
fun add(x: Int) = Instruction { it + x }

fun String.toInstruction(): List<Instruction> {
	val parts = split(" ")
	return if (parts.size == 1) listOf(noop)
	else listOf(noop, add(parts[1].toInt()))
}

fun List<String>.toInstructions() = flatMap { it.toInstruction() }

fun List<Instruction>.execute() = runningFold(1) { acc, apply -> apply(acc) }

fun List<Int>.signalStrength() = mapIndexed { i, x -> (i + 1) * x }

fun List<Int>.toLine() = mapIndexed { i, x -> if (i < x - 1 || i > x + 1) '.' else '#' }.joinToString("")

fun List<Int>.part1() = signalStrength().chunked(40) { it[19] }.sum()

fun List<Int>.part2() = chunked(40).map { it.toLine() }

fun main() {
	val lines = File("input.txt").readLines().toInstructions().execute()

	val part1 = lines.part1()
	val part2 = lines.part2()
	println("part1: $part1")
	part2.forEach(::println)
}